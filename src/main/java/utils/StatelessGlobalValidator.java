package utils;

public class StatelessGlobalValidator {

    public static void requireNonBlank(String winningNumbersInput) {
        if(winningNumbersInput == null || winningNumbersInput.isEmpty()) {
            throw new IllegalArgumentException(GlobalErrorMessage.IS_BLANK.getMessage());
        }
    }
}
