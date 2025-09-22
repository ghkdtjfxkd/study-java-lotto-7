package lotto.common.events;

import java.math.BigDecimal;

public class PurchaseEvents {

    // Producer: purchase domain, Consumer: lotto domain
    public record PurchasedLotto(long count){}

    // Producer: purchase domain, Consumer: profit domain
    public record PurchasedMade(BigDecimal price){}

    // <exception>
    // Producer: purchase domain, Consumer: input domain
    public record FailedPurchasedLottoFromWrongInput (){}
}
