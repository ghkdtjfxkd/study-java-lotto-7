package lotto.result.domain;

import java.math.BigDecimal;

public class CalculateResult {

    private final BigDecimal result;

    private CalculateResult(BigDecimal result) {
        this.result = result;
    }

    public static CalculateResult of(BigDecimal price) {
        return new CalculateResult(price);
    }

    public CalculateResult add(BigDecimal additionalPrice) {
        return new CalculateResult(this.result.add(additionalPrice));
    }

    public BigDecimal get() {
        return result;
    }
}
