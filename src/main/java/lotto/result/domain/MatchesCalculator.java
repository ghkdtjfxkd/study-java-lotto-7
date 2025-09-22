package lotto.result.domain;

import java.math.BigDecimal;

@FunctionalInterface
public interface MatchesCalculator {
    BigDecimal getTotalPrice();
}
