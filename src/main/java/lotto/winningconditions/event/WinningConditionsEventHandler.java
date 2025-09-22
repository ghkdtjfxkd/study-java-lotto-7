package lotto.winningconditions.event;

import java.util.Set;
import lotto.common.EventBus;
import lotto.common.events.InputEvents;
import lotto.common.events.InputEvents.UserEnteredBonusNumbers;
import lotto.common.events.InputEvents.UserEnteredWinningNumbers;
import lotto.common.events.WinningConditionsEvents;
import lotto.winningconditions.WinningConditionsService;

public class WinningConditionsEventHandler {

    private final EventBus eventBus;
    private final WinningConditionsService winningConditionsService;

    public WinningConditionsEventHandler(EventBus eventBus, WinningConditionsService winningConditionsService) {
        this.eventBus = eventBus;
        this.winningConditionsService = winningConditionsService;
        registerHandlers();
    }

    private void registerHandlers() {
        eventBus.subscribe(InputEvents.UserEnteredWinningNumbers.class, this::handleUserEnteredWinningNumbers);
        eventBus.subscribe(InputEvents.UserEnteredBonusNumbers.class, this::handleUserEnteredBonusNumber);
    }

    private void handleUserEnteredWinningNumbers(InputEvents.UserEnteredWinningNumbers userEnteredWinningNumbers) {
        try {
            handleSetupWinningNumbers(userEnteredWinningNumbers);
        } catch (IllegalArgumentException e) {
            handleFailedSetWinningNumbersFromWrongInput();
        }
    }

    private void handleSetupWinningNumbers(InputEvents.UserEnteredWinningNumbers userEnteredWinningNumbers) {
        winningConditionsService.setupWinningNumbers(userEnteredWinningNumbers.winningNumbers());
        Set<Integer> numbers = winningConditionsService.winningNumbers();

        eventBus.publish(new WinningConditionsEvents.WinningNumbersAreRegistered(numbers));
    }

    private void handleFailedSetWinningNumbersFromWrongInput() {
        eventBus.publish(new WinningConditionsEvents.FailedSetWinningNumbersFromWrongInput());
    }

    private void handleUserEnteredBonusNumber(InputEvents.UserEnteredBonusNumbers userEnteredBonusNumbers) {
        try {
            handleSetupBonusNumber(userEnteredBonusNumbers);
        } catch (IllegalArgumentException e) {
            handleFailedSetBonusNumbersFromWrongInput();
        }
    }

    private void handleSetupBonusNumber(InputEvents.UserEnteredBonusNumbers userEnteredBonusNumbers) {
        winningConditionsService.setupBonusNumber(userEnteredBonusNumbers.bonusNumbers());
        int number = winningConditionsService.bonusNumber();

        eventBus.publish(new WinningConditionsEvents.BonusNumberIsRegistered(number));
    }

    private void handleFailedSetBonusNumbersFromWrongInput() {
        eventBus.publish(new WinningConditionsEvents.FailedSetBonusNumberFromWrongInput());
    }
}
