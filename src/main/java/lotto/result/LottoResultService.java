package lotto.result;

import java.math.BigDecimal;
import java.util.Map;
import lotto.result.domain.BonusMatchesCalculator;
import lotto.result.domain.LottoResult;
import lotto.result.domain.MatchesCalculator;
import lotto.result.domain.NonBonusMatchesCalculator;
import lotto.result.repository.LottoResultRepository;

public class LottoResultService {

    private final LottoResultRepository lottoResultRepository;

    public LottoResultService() {
        lottoResultRepository = new LottoResultRepository();
        lottoResultRepository.setupLottoResult(LottoResult.nonMatches());
    }

    public void nonBonusResult(Map<Integer, Long> matchStatistics) {
        MatchesCalculator calculator = nonBonusMatchesCalculator(matchStatistics);
        lottoResultRepository.updateLottoResult(updateFrom(calculator));
    }

    public void bonusResult(Map<Boolean, Long> matchStatistics) {
        MatchesCalculator calculator = bonusMatchesCalculator(matchStatistics);
        lottoResultRepository.updateLottoResult(updateFrom(calculator));
    }

    private LottoResult updateFrom(MatchesCalculator matchesCalculator) {
        LottoResult lottoResult = getResult();
        return lottoResult.from(matchesCalculator);
    }

    private LottoResult getResult() {
        return lottoResultRepository.getLottoResult();
    }

    private MatchesCalculator nonBonusMatchesCalculator(Map<Integer, Long> matchStatistics) {
       return NonBonusMatchesCalculator.of(matchStatistics);
    }

    public MatchesCalculator bonusMatchesCalculator(Map<Boolean, Long> matchStatistics) {
        return BonusMatchesCalculator.of(matchStatistics);
    }

    public BigDecimal getCurrentPriceAmount() {
        return getResult().getCurrentPriceAmount();
    }
}
