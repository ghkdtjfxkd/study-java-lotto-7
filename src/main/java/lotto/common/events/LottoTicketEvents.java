package lotto.common.events;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class LottoTicketEvents {
    public record LottoTicketCreated(Entry<Long, Stream<String>> lottoTicketStatus){}
    public record NonBonusMatchesClassified(Map<Integer, Long> matchStatistics){}
    public record BonusMatchesClassified(Map<Boolean, Long> matchStatistics){}
    public record BonusSkipRecommended(){}
}
