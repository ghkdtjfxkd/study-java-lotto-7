package lotto.purchase.domain;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import utils.StatelessGlobalValidator;

public class Purchase {

    private static final Pattern DIGIT_PATTERN = Pattern.compile("^[0-9]+$");

    private final Money money;

    private Purchase(String input) {
        validate(input);
        this.money = Money.of(input);
    }

    public static Purchase of(String input) {
        return new Purchase(input);
    }

    private void validate(String input) {
        requireNonEmpty(input);
        requireOnlyDigits(input);
    }

    private void requireNonEmpty(String input) {
        StatelessGlobalValidator.requireNonBlank(input);
    }

    private void requireOnlyDigits(String input) {
        if(!isDigitsOnly(input)) {
            throw new IllegalArgumentException(ErrorMessage.MUST_BE_DIGITS.get());
        }
    }

    private boolean isDigitsOnly(String str) {
        return DIGIT_PATTERN.matcher(str).matches();
    }

    public long quantity() {
        return money.ticketCount();
    }

    public BigDecimal purchaseMade() {
        return money.amount();
    }
}
