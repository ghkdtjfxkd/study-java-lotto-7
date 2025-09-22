package lotto.profit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LottoProfit {

    private static final int ROUNDING_SCALE = 2;
    private static final String profitFormat = "총 수익률은 %f%%입니다.";
    private static final BigDecimal ZERO = new BigDecimal("0");

    private final BigDecimal purchasedMade;
    private final BigDecimal prizeMoneyAmount;

    private LottoProfit(BigDecimal purchasedMade) {
        this.purchasedMade = purchasedMade;
        this.prizeMoneyAmount = ZERO;
    }

    private LottoProfit(BigDecimal purchasedMade, BigDecimal prizeMoneyAmount) {
        this.purchasedMade = purchasedMade;
        this.prizeMoneyAmount = prizeMoneyAmount;
    }

    public static LottoProfit of(BigDecimal purchasedMade) {
        return new LottoProfit(purchasedMade);
    }

    public LottoProfit withPrizeMoneyAmount(BigDecimal amount) {
        return new LottoProfit(this.purchasedMade, this.prizeMoneyAmount.add(amount));
    }

    public String getProfit() {
        return String.format(profitFormat, purchasedMade);
    }

    public BigDecimal calculateProfitRate() {
        BigDecimal profit = prizeMoneyAmount.subtract(purchasedMade);
        BigDecimal profitRatio= profit.divide(purchasedMade, ROUNDING_SCALE, RoundingMode.HALF_UP);
        return profitRatio.multiply(new BigDecimal(100));
    }
}
