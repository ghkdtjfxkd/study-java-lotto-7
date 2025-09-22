package lotto.common.events;

import java.util.Set;

public class WinningConditionsEvents {

    public record WinningNumbersAreRegistered(Set<Integer> winningNumbers) {}
    public record BonusNumberIsRegistered(int bonusNumber) {}

    public record FailedSetWinningNumbersFromWrongInput(){}
    public record FailedSetBonusNumberFromWrongInput(){}
}
