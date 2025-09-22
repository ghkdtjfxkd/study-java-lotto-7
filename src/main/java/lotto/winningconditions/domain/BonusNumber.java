package lotto.winningconditions.domain;

import utils.StatelessGlobalLottoRulesValidator;

public class BonusNumber {

    private static final int DEFAULT_BONUS_NUMBER = 0;

    private final int bonusNumber;

    private BonusNumber() {
        this.bonusNumber = DEFAULT_BONUS_NUMBER;
    }

    private BonusNumber(String bonusNumberInput) {
        this.bonusNumber = validatedNumber(bonusNumberInput);
    }

    public static BonusNumber nonDefined() {
        return new BonusNumber();
    }

    public static BonusNumber from(String bonusNumberInput) {
        return new BonusNumber(bonusNumberInput);
    }

    public int getNumber() {
        return bonusNumber;
    }

    private int validatedNumber (String bonusNumberInput) {
        int inputNumber = validatedInputNumberByIntegerNumberRange(bonusNumberInput);
        requireLottoNumberRange(inputNumber);
        return inputNumber;
    }

    private void requireLottoNumberRange(int inputNumber) {
        StatelessGlobalLottoRulesValidator.requireLottoNumberRange(inputNumber);
    }

    private int validatedInputNumberByIntegerNumberRange(String bonusNumberInput) {
        StatelessGlobalLottoRulesValidator.requireIntegerNumberRangeForInput(bonusNumberInput);
        return Integer.parseInt(bonusNumberInput);
    }
}
