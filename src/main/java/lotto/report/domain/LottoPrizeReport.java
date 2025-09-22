package lotto.report.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

// 당첨 갯수 파싱
public class LottoPrizeReport {

    private static final String PRIZE_AND_COUNT_FORMAT = "%s - %d개";
    private static final int BONUS_NUMBER_EXIST_CONDITION_THRESHOLD = 5;
    private static final long NON_COUNTED = 0;
    private static final boolean BONUS_NUMBERS_REQUIRED = false;

    private final Map<Status, Long> prizes;

    private LottoPrizeReport() {
        this.prizes = nonPrizes();
    }

    private LottoPrizeReport(Map<Status, Long> prizes) {
        this.prizes = Map.copyOf(prizes);
    }

    public static LottoPrizeReport defaultReport() {
        return new LottoPrizeReport();
    }

    private Map<Status, Long> nonPrizes() {
        Map<Status,Long> prizeMap = new EnumMap<>(Status.class);

        for (Status status : Status.values()) {
            prizeMap.put(status, NON_COUNTED);
        }
        return prizeMap;
    }

    public LottoPrizeReport updateNonBonusMatchesPrizeStatus(Map<Integer, Long> matchStatistics) {
        matchStatistics.forEach((matchedNumbers, matchTimes) ->
                prizes.replace(Status.getFrom(matchedNumbers ,BONUS_NUMBERS_REQUIRED), matchTimes));

        return new LottoPrizeReport(this.prizes);
    }

    public LottoPrizeReport updateBonusMatchesPrizeStatus(Map<Boolean, Long> matchStatistics) {
        matchStatistics.forEach((matchedNumbers, matchTimes) ->
                prizes.replace(Status.getFrom(BONUS_NUMBER_EXIST_CONDITION_THRESHOLD, matchedNumbers), matchTimes));

        return new LottoPrizeReport(this.prizes);
    }

    public List<String> getPrizes() {
        return prizes.entrySet()
                .stream()
                .map(this::parsedFrom)
                .toList();
    }

    private String parsedFrom(Entry<Status, Long> entry) {
        return String.format(PRIZE_AND_COUNT_FORMAT,
                Status.getStatisticsFormat(entry.getKey()),
                entry.getValue());
    }

    private enum Status {
        FIRST_PRIZE(6, false, 2_000_000_000L),
        SECOND_PRIZE(5, true, 30_000_000L),
        THIRD_PRIZE(5, false, 1_500_000L),
        FOURTH_PRIZE(4, false, 50_000L),
        FIFTH_PRIZE(3, false, 5_000L);

        private static final String STATISTICS_FORMAT = "%d개 일치";
        private static final String BONUS_STATISTICS_FORMAT = "%d개 일치, 보너스 볼 일치";
        private static final String PRIZE_MONEY_FORMAT = "(%,d원)";

        private static final Map<Integer, Status> MATCH_COUNT_MAP;
        private static final int BONUS_NUMBER_EXIST_CONDITION_THRESHOLD = 5;

        static {
            MATCH_COUNT_MAP = Arrays.stream(values())
                    .filter(prize -> !prize.bonusNumberRequired)
                    .collect(Collectors.toMap(Status::matchedCount, prize -> prize));
        }

        private final int matchedCount;
        private final boolean bonusNumberRequired;
        private final long prizeMoney;

        Status(int matchedCount, boolean bonusNumberRequired, long prizeMoney) {
            this.matchedCount = matchedCount;
            this.bonusNumberRequired = bonusNumberRequired;
            this.prizeMoney = prizeMoney;
        }

        static Status getFrom(int matchedCount, boolean bonusNumberRequired) {
            if(matchedCount == BONUS_NUMBER_EXIST_CONDITION_THRESHOLD) {
                return getFromSecondaryCondition(bonusNumberRequired);
            }
            return MATCH_COUNT_MAP.get(matchedCount);
        }

        private static Status getFromSecondaryCondition(boolean bonusNumberRequired) {
            if(bonusNumberRequired) {
                return Status.SECOND_PRIZE;
            }
            return MATCH_COUNT_MAP.get(BONUS_NUMBER_EXIST_CONDITION_THRESHOLD);
        }

        private int matchedCount() {
            return matchedCount;
        }

        private long prizeMoney() {
            return prizeMoney;
        }

        static String getStatisticsFormat(Status status) {
            if(status == SECOND_PRIZE) {
                return String.format(BONUS_STATISTICS_FORMAT, status.matchedCount()) + " " + String.format(PRIZE_MONEY_FORMAT, status.prizeMoney());
            }
            return String.format(STATISTICS_FORMAT, status.matchedCount()) + " " + String.format(PRIZE_MONEY_FORMAT, status.prizeMoney());
        }
    }
//    private enum Status {
//        FIRST_PRIZE(6, 2_000_000_000L),
//        SECOND_PRIZE(5, 30_000_000L),
//        THIRD_PRIZE(5, 1_500_000L),
//        FOURTH_PRIZE(4, 50_000L),
//        FIFTH_PRIZE(3, 5_000L);
//
//        private static final String STATISTICS_FORMAT = "%d개 일치 (%s) - %d개";
//        private static final String BONUS_STATISTICS_FORMAT = "%d개 일치, 보너스 볼 일치 (%s) - %d개";
//        private static final Map<Integer, Status> MATCH_COUNT_MAP;
//        private static final int BONUS_NUMBER_EXIST_CONDITION_THRESHOLD = 5;
//
//        static {
//            MATCH_COUNT_MAP = Arrays.stream(values())
//                    .filter(prize -> prize.matchedCount != BONUS_NUMBER_EXIST_CONDITION_THRESHOLD)
//                    .collect(Collectors.toMap(Status::matchedCount, prize -> prize));
//        }
//        private final int matchedCount;
//        private final BigDecimal prize;
//
//        Status(int matchedCount, long prize) {
//            this.matchedCount = matchedCount;
//            this.prize = new BigDecimal(prize);
//        }
//
//        static Status getFrom(int matchedCount) {
//            return MATCH_COUNT_MAP.get(matchedCount);
//        }
//
//        private int matchedCount() {
//            return matchedCount;
//        }
//    }
}
