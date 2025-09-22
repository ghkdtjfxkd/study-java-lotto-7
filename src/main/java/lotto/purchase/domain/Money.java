package lotto.purchase.domain;

import java.math.BigDecimal;

public class Money {

    private static final long DIVISION_SCALE = 1000L;
    private static final int ZERO = 0;

    private final long amount;

    public Money(String input) {
        validate(input);
        this.amount = Long.parseLong(input);
    }

    public static Money of(String input) {
        return new Money(input);
    }

    private void validate(String input) {
        BigDecimal number = new BigDecimal(input);

        requirePositiveNumber(number);
        requireLongRange(number);
        requireCorrectNumericUnit(number);
    }

    private void requirePositiveNumber(BigDecimal number) {
        if(number.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ErrorMessage.IS_MUST_BE_GREATER_THAN_ZERO.get());
        }
    }

    private void requireLongRange(BigDecimal number) {
        if(number.compareTo(new BigDecimal(Long.MAX_VALUE)) > 0) {
            throw new IllegalArgumentException(ErrorMessage.IS_OUT_OF_LONG_RANGE.get());
        }
    }

    private void requireCorrectNumericUnit(BigDecimal number) {
        double num = number.doubleValue();

        if(num % DIVISION_SCALE != ZERO) {
            throw new IllegalArgumentException(ErrorMessage.MUST_DIVISION_THOUSAND.get());
        }
    }

    public BigDecimal amount() {
        return new BigDecimal(amount);
    }

    public long ticketCount() {
        return amount / DIVISION_SCALE;
    }
}
