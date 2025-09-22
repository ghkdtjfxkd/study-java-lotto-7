package lotto.result.event;

import java.util.Map;
import lotto.common.EventBus;
import lotto.common.events.LottoResultEvents;
import lotto.common.events.LottoTicketEvents;
import lotto.common.events.LottoTicketEvents.BonusMatchesClassified;
import lotto.common.events.LottoTicketEvents.NonBonusMatchesClassified;
import lotto.result.LottoResultService;

public class ResultEventHandler {

    private final EventBus eventBus;
    private final LottoResultService lottoResultService;

    public ResultEventHandler(EventBus eventBus, LottoResultService lottoResultService) {
        this.eventBus = eventBus;
        this.lottoResultService = lottoResultService;
        registerHandlers();
    }

    private void registerHandlers() {
        eventBus.subscribe(LottoTicketEvents.NonBonusMatchesClassified.class, this::handleNonBonusMatchesClassified);
        eventBus.subscribe(LottoTicketEvents.BonusMatchesClassified.class, this::handleBonusMatchesClassified);
        eventBus.subscribe(LottoTicketEvents.BonusSkipRecommended.class, this::handleBonusMatchesClassified);
    }

    private void handleNonBonusMatchesClassified(NonBonusMatchesClassified nonBonusMatchesClassified) {
        lottoResultService.nonBonusResult(nonBonusMatchesClassified.matchStatistics());
        eventBus.publish(new LottoResultEvents.CalculatedNonBonusMatches(lottoResultService.getCurrentPriceAmount()));
    }

    private void handleBonusMatchesClassified(BonusMatchesClassified bonusMatchesClassified) {
        lottoResultService.bonusResult(bonusMatchesClassified.matchStatistics());
        eventBus.publish(new LottoResultEvents.CompletedCalculate(lottoResultService.getCurrentPriceAmount()));
    }

    private void handleBonusMatchesClassified(LottoTicketEvents.BonusSkipRecommended bonusSkipRecommended) {
        eventBus.publish(new LottoResultEvents.CompletedCalculate(lottoResultService.getCurrentPriceAmount()));
    }
}
