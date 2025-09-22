package lotto.lottoticket.repository;

import lotto.lottoticket.domain.LottoTicket;

public class LottoTicketRepository {

    private LottoTicket lottoTicket;

    public void setup(LottoTicket lottoTicket) {
        this.lottoTicket = lottoTicket;
    }

    public LottoTicket update(LottoTicket lottoTicket) {
        this.lottoTicket = lottoTicket;
        return lottoTicket;
    }

    public LottoTicket getLottoTicket() {
        return lottoTicket;
    }
}
