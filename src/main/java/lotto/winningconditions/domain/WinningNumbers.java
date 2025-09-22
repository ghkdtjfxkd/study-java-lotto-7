package lotto.winningconditions.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import utils.StatelessGlobalLottoRulesValidator;

public class WinningNumbers {

    private final static String DELIMITER = ",";
    private final Set<Integer> numbers;

    private WinningNumbers(String winningNumbersInput) {
        this.numbers = parsedFrom(winningNumbersInput);
        afterValidate();
    }

    public static WinningNumbers from(String winningNumbersInput) {
        return new WinningNumbers(winningNumbersInput);
    }

    private Set<Integer> parsedFrom(String winningNumbersInput) {
        return Arrays.stream(winningNumbersInput.split(DELIMITER))
                .map(this::validatedNumberFrom)
                .collect(Collectors.toSet());
    }

    private int validatedNumberFrom(String token) {
        int tokenNumber = parsedToken(token);

        StatelessGlobalLottoRulesValidator.requireLottoNumberRange(tokenNumber);
        return tokenNumber;
    }

    private int parsedToken(String token) {
        StatelessGlobalLottoRulesValidator.requireIntegerNumberRangeForInput(token);
        return Integer.parseInt(token);
    }

    private void afterValidate() {
        StatelessGlobalLottoRulesValidator.requireCorrectBallCount(this.numbers);
    }

    public Set<Integer> get() {
        return Set.copyOf(this.numbers);
    }

    public boolean include(int inputNumber) {
        return this.numbers.contains(inputNumber);
    }
}
