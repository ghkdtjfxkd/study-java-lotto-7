package utils;

enum GlobalErrorMessage {
    IS_BLANK("빈 입력은 허용되지 않습니다."),
    INVALID_INTEGER_RANGE("정수 값으로 변환 불가능한 입력입니다."),
    INVALID_LOTTO_NUMBER_RANGE("%d 와 %d 사이의 숫자만 입력 가능합니다."),
    INVALID_BALL_COUNT("로또 번호는 %d개 입니다.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    GlobalErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        if (message.contains("%d")) {
            throw new IllegalStateException("이 메시지는 포맷팅이 필요합니다: " + name());
        }
        return PREFIX + message;
    }

    // 동적 값 포맷팅 용
    public String getFormattedMessage(Object... args) {
        return PREFIX + String.format(message, args);
    }
}
