package lotto.common.events;

import java.math.BigDecimal;

public class LottoResultEvents {

    public record CalculatedNonBonusMatches(BigDecimal nonBonusMatchesPrizeMoney){}
    public record CompletedCalculate(BigDecimal totalPrizeMoney){}
}
