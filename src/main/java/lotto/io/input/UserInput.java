package lotto.io.input;

import camp.nextstep.edu.missionutils.Console;

public class UserInput {

    public static String money() {
        return inputFrom(GuideMessage.FOR_MONEY);
    }

    public static String luckyNumber() {
        return inputFrom(GuideMessage.FOR_LUCKY_NUMBER);
    }

    public static String bonusNumber() {
        return inputFrom(GuideMessage.FOR_BONUS_NUMBER);
    }

    private static String inputFrom(GuideMessage guidemessage) {
        System.out.println(guidemessage.message);
        return Console.readLine();
    }

    public static void close() {
        Console.close();
    }

    private enum GuideMessage {
        FOR_MONEY("구입금액을 입력해 주세요."),
        FOR_LUCKY_NUMBER("당첨 번호를 입력해 주세요."),
        FOR_BONUS_NUMBER("보너스 번호를 입력해 주세요.");

        final String message;

        GuideMessage(String message) {
            this.message = message;
        }

        String get() {
            return message;
        }
    }
}
