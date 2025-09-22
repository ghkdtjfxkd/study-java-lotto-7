package lotto.winningconditions.domain;

import java.util.Set;
import utils.StatelessGlobalValidator;

public class WinningConditions {

    private final WinningNumbers winningNumbers;
    private final BonusNumber bonusNumber;

    public WinningConditions(String winningNumbersInput) {
        validateInput(winningNumbersInput);

        this.winningNumbers = WinningNumbers.from(winningNumbersInput);
        this.bonusNumber = BonusNumber.nonDefined();
    }

    private WinningConditions(WinningNumbers winningNumbers, String bonusNumberInput) {
        validateBonusNumberInput(bonusNumberInput);

        this.winningNumbers = winningNumbers;
        this.bonusNumber = BonusNumber.from(bonusNumberInput);
    }

    // 당첨 번호 입력 시
    public static WinningConditions setupWinningNumbers(String winningNumbersInput) {
        return new WinningConditions(winningNumbersInput);
    }

    // 보너스 번호 입력시
    public WinningConditions setupBonusNumber(String bonusNumberInput) {
        return new WinningConditions(winningNumbers, bonusNumberInput);
    }

    private void validateBonusNumberInput(String bonusNumberInput) {
        validateInput(bonusNumberInput);
        validateBonusNumber(bonusNumberInput);
    }

    private void validateInput(String input) {
       requireNonBlank(input);
    }

    private void requireNonBlank(String winningNumbersInput) {
        StatelessGlobalValidator.requireNonBlank(winningNumbersInput);
    }

    private void validateBonusNumber(String bonusNumbersInput) {
        int inputNumber = Integer.parseInt(bonusNumbersInput);
        if(winningNumbers.include(inputNumber)){
            throw new IllegalArgumentException("입력하신 보너스 번호는 당첨 번호와 겹치면 안됩니다.");
        }
    }

    public Set<Integer> winningNumbers() {
        return Set.copyOf(winningNumbers.get());
    }

    public int bonusNumber() {
        return bonusNumber.getNumber();
    }
}
