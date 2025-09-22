package lotto.purchase.event;

import lotto.common.EventBus;
import lotto.common.events.InputEvents;
import lotto.common.events.InputEvents.UserEnteredMoney;
import lotto.common.events.PurchaseEvents;
import lotto.purchase.service.PurchaseService;

public class PurchaseEventHandler {

    private final EventBus eventBus;
    private final PurchaseService purchaseService;

    public PurchaseEventHandler(EventBus eventBus, PurchaseService purchaseService) {
        this.eventBus = eventBus;
        this.purchaseService = purchaseService;
        registerHandlers();
    }

    private void registerHandlers() {
        eventBus.subscribe(InputEvents.UserEnteredMoney.class, this::handleUserEnteredMoney);
    }

    private void handleUserEnteredMoney(UserEnteredMoney userEnteredMoney) {
        try {
            purchaseService.createPurchase(userEnteredMoney.money());
            eventBus.publish(new PurchaseEvents.PurchasedLotto(purchaseService.purchaseCount()));
            eventBus.publish(new PurchaseEvents.PurchasedMade(purchaseService.purchasedMoney()));
        } catch (IllegalArgumentException e) {
            handleInputException(e);
        }
    }

    private void handleInputException(IllegalArgumentException e) {
        purchaseService.clearPurchase();
        System.err.println(e.getMessage());
        eventBus.publish(new PurchaseEvents.FailedPurchasedLottoFromWrongInput());
    }
}
