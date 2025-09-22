package lotto.io.input;

import lotto.common.EventBus;
import lotto.common.events.InputEvents;
import lotto.common.events.OutputEvents;
import lotto.common.events.OutputEvents.PrintedLottoTicket;
import lotto.common.events.PurchaseEvents;
import lotto.common.events.WinningConditionsEvents;
import lotto.common.events.WinningConditionsEvents.WinningNumbersAreRegistered;

public class InputEventHandler {

    private final EventBus eventBus;

    public InputEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
        registerHandlers();
    }

    private void registerHandlers() {
        eventBus.subscribe(InputEvents.UserEnteredMoney.class, this::handleUserEnteredMoney);
        eventBus.subscribe(PurchaseEvents.FailedPurchasedLottoFromWrongInput.class, this::handleUserEnteredMoney);

        eventBus.subscribe(OutputEvents.PrintedLottoTicket.class, this::handleUserEnteredWinningNumbers);
        eventBus.subscribe(WinningConditionsEvents.FailedSetWinningNumbersFromWrongInput.class, this::handleUserEnteredWinningNumbers);

        eventBus.subscribe(WinningConditionsEvents.WinningNumbersAreRegistered.class, this::handleUserEnteredBonusNumber);
        eventBus.subscribe(WinningConditionsEvents.FailedSetBonusNumberFromWrongInput.class, this::handleUserEnteredBonusNumber);
    }

    private void handleUserEnteredMoney(InputEvents.UserEnteredMoney userEnteredMoney) {
        eventBus.publish(new InputEvents.UserEnteredMoney(UserInput.money()));
    }

    private void handleUserEnteredMoney(PurchaseEvents.FailedPurchasedLottoFromWrongInput failedPurchasedLottoFromWrongInput) {
        eventBus.publish(new InputEvents.UserEnteredMoney(UserInput.money()));
    }

    private void handleUserEnteredWinningNumbers(PrintedLottoTicket printedLottoTicket) {
        eventBus.publish(new InputEvents.UserEnteredWinningNumbers(UserInput.winningNumbers()));
    }

    private void handleUserEnteredWinningNumbers(WinningConditionsEvents.FailedSetWinningNumbersFromWrongInput failedSetWinningNumbersFromWrongInput) {
        eventBus.publish(new InputEvents.UserEnteredWinningNumbers(UserInput.winningNumbers()));
    }

    private void handleUserEnteredBonusNumber(WinningNumbersAreRegistered winningNumbersAreRegistered) {
        eventBus.publish(new InputEvents.UserEnteredBonusNumbers(UserInput.bonusNumber()));
    }

    private void handleUserEnteredBonusNumber(WinningConditionsEvents.FailedSetBonusNumberFromWrongInput failedSetBonusNumberFromWrongInput) {
        eventBus.publish(new InputEvents.UserEnteredBonusNumbers(UserInput.bonusNumber()));
    }



}
