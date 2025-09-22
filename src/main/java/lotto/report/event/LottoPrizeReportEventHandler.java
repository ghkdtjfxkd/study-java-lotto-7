package lotto.report.event;

import lotto.common.EventBus;
import lotto.common.events.LottoTicketEvents;
import lotto.report.LottoPrizeReportService;

// 당첨 갯수대한 부분
public class LottoPrizeReportEventHandler {

    private final EventBus eventBus;
    private final LottoPrizeReportService lottoPrizeReportService;

    public LottoPrizeReportEventHandler(EventBus eventBus, LottoPrizeReportService lottoPrizeReportService) {
        this.eventBus = eventBus;
        this.lottoPrizeReportService = lottoPrizeReportService;
        registerHandlers();
    }

    private void registerHandlers() {
        eventBus.subscribe(LottoTicketEvents.NonBonusMatchesClassified.class, this::handleCalculatedNonBonusMatches);
    }

    private void handleCalculatedNonBonusMatches(LottoTicketEvents.NonBonusMatchesClassified event) {
//        lottoPrizeReportService.apply(event.matchStatistics());
    }
}
