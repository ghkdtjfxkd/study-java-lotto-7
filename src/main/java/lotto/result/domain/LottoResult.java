package lotto.result.domain;

import java.math.BigDecimal;

public class LottoResult {

    private final static BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;

    private final BigDecimal priceAmount;
    private final MatchesCalculator matchesCalculator;

    private LottoResult() {
        this.priceAmount = DEFAULT_PRICE;
        this.matchesCalculator = NonBonusMatchesCalculator.unused();
    }

    private LottoResult(BigDecimal priceAmount, MatchesCalculator matchesCalculator) {
        this.priceAmount = priceAmount;
        this.matchesCalculator = matchesCalculator;
    }

    // 아무것도 당첨되지 않았을 때 결과 생성에 사용
    public static LottoResult nonMatches() {
        return new LottoResult();
    }

    // 처음 계산기가 들어올 경우에 사용
    public LottoResult from(MatchesCalculator matchesCalculator) {
        return new LottoResult(this.priceAmount, matchesCalculator);
    }

    // 계산기가 이미 들어와 있는 경우,새 계산기로 바꾸고 기존 상금에 새 계산 결과를 더하는데 사용
    public LottoResult withMatchesCalculator(MatchesCalculator matchesCalculator) {
        return new LottoResult(this.priceAmount.add(calculateResult()), matchesCalculator);
    }

    private BigDecimal calculateResult() {
        return matchesCalculator.getTotalPrice();
    }

    public BigDecimal getCurrentPriceAmount() {
        return priceAmount.add(calculateResult());
    }
}
