package utils;

import java.util.Collection;
import java.util.List;

public class StatelessGlobalLottoRulesValidator {

    public static void requireIntegerNumberRangeForInput(String bonusNumberInput) {
        try {
            Integer.parseInt(bonusNumberInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(GlobalErrorMessage.INVALID_INTEGER_RANGE.getMessage());
        }
    }

    public static void requireLottoNumberRange(int inputNumber) {
        if (!isInLottoNumberRange(inputNumber)) {
            throw new IllegalArgumentException(GlobalErrorMessage.INVALID_LOTTO_NUMBER_RANGE
                    .getFormattedMessage(LottoRules.MIN_NUMBER, LottoRules.MAX_NUMBER));
        }
    }

    private static boolean isInLottoNumberRange(int inputNumber) {
        return  LottoRules.MIN_NUMBER.value() <=inputNumber && inputNumber <= LottoRules.MAX_NUMBER.value();
    }

    public static void requireCorrectBallCount(Collection<Integer> numbers) {
        if (!hasSixBall(numbers)) {
            throw new IllegalArgumentException(GlobalErrorMessage.INVALID_BALL_COUNT
                    .getFormattedMessage(LottoRules.LOTTO_BALL_COUNT.value()));
        }
    }

    private static boolean hasSixBall(Collection<Integer> numbers) {
        return numbers.size() == LottoRules.LOTTO_BALL_COUNT.value();
    }

    public static void requireUniqueNumbers(List<Integer> numbers) {
        if(!hasUniqueNumbers(numbers)) {
            throw new IllegalArgumentException("동일한 숫자는 존재할 수 없습니다.");
        }
    }

    private static boolean hasUniqueNumbers(List<Integer> numbers) {
        return numbers.stream().distinct().count() == LottoRules.LOTTO_BALL_COUNT.value();
    }


    private enum LottoRules {
        MIN_NUMBER(1),
        MAX_NUMBER(45),
        LOTTO_BALL_COUNT(6);

        private final int value;

        LottoRules(int value) {
            this.value = value;
        }

        int value() {
            return value;
        }
    }
}
