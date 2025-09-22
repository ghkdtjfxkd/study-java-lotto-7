package lotto.common.events;

public class InputEvents {

    // <Starter>
    // Producer: input domain, Consumer: purchase domain
    public record UserEnteredMoney(String money){}

    public record UserEnteredWinningNumbers(String winningNumbers){}

    public record UserEnteredBonusNumbers(String bonusNumbers){}
}
