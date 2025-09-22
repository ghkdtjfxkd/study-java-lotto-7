package lotto.io.output;

import lotto.common.EventBus;
import lotto.common.events.LottoTicketEvents;
import lotto.common.events.LottoTicketEvents.LottoTicketCreated;
import lotto.common.events.OutputEvents;

public class OutputEventHandler {

    private final EventBus eventBus;

    public OutputEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
        registerHandlers();
    }

    private void registerHandlers() {
        eventBus.subscribe(LottoTicketEvents.LottoTicketCreated.class, this::handleLottoTicketCreated);
    }

    private void handleLottoTicketCreated(LottoTicketCreated lottoTicketCreated) {
//        OutputView.buyLotto(lottoTicketCreated.lottoTicketStatus());
        eventBus.publish(new OutputEvents.PrintedLottoTicket());
    }
}
