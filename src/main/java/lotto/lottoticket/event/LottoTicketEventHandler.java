package lotto.lottoticket.event;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import lotto.common.EventBus;
import lotto.common.events.LottoTicketEvents;
import lotto.common.events.PurchaseEvents;
import lotto.common.events.WinningConditionsEvents;
import lotto.lottoticket.LottoTicketService;

public class LottoTicketEventHandler {

    private final EventBus eventBus;
    private final LottoTicketService lottoTicketService;

    public LottoTicketEventHandler(EventBus eventBus, LottoTicketService lottoTicketService) {
        this.eventBus = eventBus;
        this.lottoTicketService = lottoTicketService;
        registerHandlers();
    }

    private void registerHandlers() {
        eventBus.subscribe(PurchaseEvents.PurchasedLotto.class, this::handlePurchaseLotto);
        eventBus.subscribe(WinningConditionsEvents.WinningNumbersAreRegistered.class, this::handleWinningNumbersAreRegistered);
        eventBus.subscribe(WinningConditionsEvents.BonusNumberIsRegistered.class, this::handleBonusNumberIsRegistered);
    }

    private void handlePurchaseLotto(PurchaseEvents.PurchasedLotto purchasedLotto) {
        Entry<Long, Stream<String>> purchasedLog = lottoTicketService.purchasedLottoTicket(purchasedLotto.count());
        eventBus.publish(new LottoTicketEvents.LottoTicketCreated(purchasedLog));
    }

    private void handleWinningNumbersAreRegistered(WinningConditionsEvents.WinningNumbersAreRegistered winningNumbersAreRegistered) {
        Map<Integer,Long> matchStatistics = lottoTicketService.classifyNonBonusMatches(winningNumbersAreRegistered.winningNumbers());
        eventBus.publish(new LottoTicketEvents.NonBonusMatchesClassified(matchStatistics));
    }

    private void handleBonusNumberIsRegistered(WinningConditionsEvents.BonusNumberIsRegistered bonusNumberIsRegistered) {
        if(lottoTicketService.canSkipStatisticBonusMatches()) {
            eventBus.publish(new LottoTicketEvents.BonusSkipRecommended());
            return;
        }

        Map<Boolean,Long> matchStatistics = lottoTicketService.classifyBonusMatches(bonusNumberIsRegistered.bonusNumber());
        eventBus.publish(new LottoTicketEvents.BonusMatchesClassified(matchStatistics));
    }
}
