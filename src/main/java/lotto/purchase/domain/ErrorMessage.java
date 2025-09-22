package lotto.purchase.domain;

enum ErrorMessage {

    IS_BLANK("빈 입력은 허용되지 않습니다."),
    MUST_BE_DIGITS("구매 금액은 숫자만을 입력해야 합니다."),

    IS_MUST_BE_GREATER_THAN_ZERO("0 보다 큰 값을 입력해야 합니다."),
    IS_OUT_OF_LONG_RANGE("구매 금액의 범위는 Long 범위를 넘어설 수 없습니다."),
    MUST_DIVISION_THOUSAND("구매 금액은 1000으로 나누어 떨어지는 숫자만을 입력해야 합니다.");

    private static final String ERROR_FORMAT = "[ERROR] %s";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    String get() {
        return String.format(ERROR_FORMAT, message);
    }
}
