package lotto.result.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BonusMatchesCalculator implements MatchesCalculator {

    private final static BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;

    private final BigDecimal total;

    private BonusMatchesCalculator() {
        total = DEFAULT_PRICE;
    }

    private BonusMatchesCalculator(Map<Boolean, Long> matchStatistics) {
        this.total = totalPrice(matchStatistics);
    }

    public static BonusMatchesCalculator unused() {
        return new BonusMatchesCalculator();
    }

    public static BonusMatchesCalculator of(Map<Boolean, Long> matchStatistics) {
        return new BonusMatchesCalculator(matchStatistics);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return total;
    }

    private BigDecimal totalPrice(Map<Boolean, Long> matchState) {
        if (matchState.isEmpty()) {
            return DEFAULT_PRICE;
        }

        return matchState.entrySet().stream()
                .map(this::matchesTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal matchesTotalPrice(Entry<Boolean, Long> matchStatistics) {
        boolean matchCount = matchStatistics.getKey();
        BigDecimal matchTimes = new BigDecimal(matchStatistics.getValue());

        return invertToPrice(matchCount).multiply(matchTimes);
    }

    private BigDecimal invertToPrice(boolean matchCount) {
        return Prize.getPrizeAmount(matchCount);
    }

    private enum Prize {

        // 나머지 등수는 NonBonusMatchCalculator 에서 처리
        SECOND_PRIZE(true,30_000_00L),
        THRID_PRIZE(false,1_500_000L);

        private final boolean contained;
        private final BigDecimal prizeAmount;

        private static final Map<Boolean,Prize> MATCH_COUNT_MAP;

        static {
            MATCH_COUNT_MAP = Arrays.stream(values())
                    .collect(Collectors.toMap(Prize::contained, prize -> prize));
        }

        Prize(boolean contained, long price) {
            this.contained = contained;
            this.prizeAmount = new BigDecimal(price);
        }

        static BigDecimal getPrizeAmount(boolean bonusBallContained) {
            Prize prize = MATCH_COUNT_MAP.get(bonusBallContained);
            return prize.price();
        }

        private boolean contained() {
            return this.contained;
        }

        private BigDecimal price() {
            return this.prizeAmount;
        }
    }
}
