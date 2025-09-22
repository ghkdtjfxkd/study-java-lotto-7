package lotto.lottoticket;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import lotto.lottoticket.domain.LottoTicket;
import lotto.lottoticket.repository.LottoTicketRepository;

public class LottoTicketService {

    private final LottoTicketRepository lottoTicketRepository;

    public LottoTicketService() {
        this.lottoTicketRepository = new LottoTicketRepository();
    }

    public Entry<Long, Stream<String>> purchasedLottoTicket(long purchaseCount) {
        lottoTicketRepository.setup(LottoTicket.buying(purchaseCount));
        return lottoTicketRepository.getLottoTicket().purchasedLottoLog();
    }

    public Map<Integer, Long> classifyNonBonusMatches(Collection<Integer> numbers) {
        LottoTicket lottoTicket = storedLottoTicket();
        Map<Integer, Long> matchesStatistic = lottoTicket.matchedTable(numbers);

        lottoTicketRepository.setup(lottoTicket.leftOver());
        return matchesStatistic;
    }

    public Map<Boolean, Long> classifyBonusMatches(int number) {
        LottoTicket lottoTicket = storedLottoTicket();
        Map<Boolean, Long> matchesStatistic = lottoTicket.matchedTable(number);

        lottoTicketRepository.setup(lottoTicket.leftOver());
        return matchesStatistic;
    }

    private LottoTicket storedLottoTicket() {
        return lottoTicketRepository.getLottoTicket();
    }

    public boolean canSkipStatisticBonusMatches() {
        return !storedLottoTicket().hasUnclassifiedLotto();
    }
}
