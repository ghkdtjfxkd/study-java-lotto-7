package lotto.result.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class NonBonusMatchesCalculator implements MatchesCalculator {

    private final static BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;
    private final BigDecimal total;

    private NonBonusMatchesCalculator() {
        this.total = DEFAULT_PRICE;
    }

    private NonBonusMatchesCalculator(Map<Integer, Long> matchStatistics) {
        this.total = totalPrice(matchStatistics);
    }

    public static NonBonusMatchesCalculator unused() {
        return new NonBonusMatchesCalculator();
    }

    public static NonBonusMatchesCalculator of(Map<Integer, Long> matchStatistics) {
        return new NonBonusMatchesCalculator(matchStatistics);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return total;
    }

    private BigDecimal totalPrice(Map<Integer, Long> matchState) {
        return matchState.entrySet().stream()
                .map(this::matchesTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal matchesTotalPrice(Entry<Integer, Long> matchState) {
        int matchCount = matchState.getKey();
        BigDecimal matchTimes = new BigDecimal(matchState.getValue());

        return invertToPrice(matchCount).multiply(matchTimes);
    }

    private BigDecimal invertToPrice(int matchCount) {
        return Prize.getPrizeAmount(matchCount);
    }

    private enum Prize {
        // 2, 3등은 BonusMatchCalculator 에서 처리
        FIRST_PRIZE(6, 2_000_000_000L),
        FOURTH_PRIZE(4, 50_000L),
        FIFTH_PRIZE(3, 5_000L),

        NO_PRIZE(0, 0L);

        private final int matchedCount;
        private final BigDecimal prizeAmount;

        private static final Map<Integer, Prize> MATCH_COUNT_MAP;

        static {
            MATCH_COUNT_MAP = Arrays.stream(values())
                    .filter(prize -> prize != NO_PRIZE)
                    .collect(Collectors.toMap(Prize::matchedCount, prize -> prize));
        }

        Prize(int matchedCount, long price) {
            this.matchedCount = matchedCount;
            this.prizeAmount = new BigDecimal(price);
        }

        static BigDecimal getPrizeAmount(int matchedCount) {
            Prize prize = MATCH_COUNT_MAP.getOrDefault(matchedCount, NO_PRIZE);
            return prize.price();
        }

        private int matchedCount() {
            return matchedCount;
        }

        private BigDecimal price() {
            return prizeAmount;
        }
    }
}
