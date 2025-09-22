package lotto.winningconditions.repostiory;

import lotto.winningconditions.domain.WinningConditions;

public class WinningConditionsRepository {

    private WinningConditions winningConditions;

    public void setup(WinningConditions winningConditions) {
        this.winningConditions = winningConditions;
    }

    public void update(WinningConditions winningConditions) {
        this.winningConditions = winningConditions;
    }

    public WinningConditions get() {
        return winningConditions;
    }
}
