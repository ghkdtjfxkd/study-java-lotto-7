package lotto.winningconditions;

import java.util.Set;
import lotto.winningconditions.domain.WinningConditions;
import lotto.winningconditions.repostiory.WinningConditionsRepository;

public class WinningConditionsService {

    private final WinningConditionsRepository winningConditionsRepository;

    public WinningConditionsService() {
        this.winningConditionsRepository = new WinningConditionsRepository();
    }

    public void setupWinningNumbers(String winningNumbersInput) {
        winningConditionsRepository.setup(WinningConditions.setupWinningNumbers(winningNumbersInput));
    }

    public Set<Integer> winningNumbers() {
        WinningConditions winningConditions = winningConditionsRepository.get();
        return winningConditions.winningNumbers();
    }

    public void setupBonusNumber(String bonusNumberInput) {
        WinningConditions stored = winningConditionsRepository.get();
        winningConditionsRepository.update(stored.setupBonusNumber(bonusNumberInput));
    }

    public int bonusNumber() {
        WinningConditions winningConditions = winningConditionsRepository.get();
        return winningConditions.bonusNumber();
    }
}
