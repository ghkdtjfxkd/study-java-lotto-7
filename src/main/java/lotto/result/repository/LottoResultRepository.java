package lotto.result.repository;

import lotto.result.domain.LottoResult;

public class LottoResultRepository {

    private LottoResult lottoResult;

    public void setupLottoResult(LottoResult lottoResult) {
        this.lottoResult = lottoResult;
    }

    public LottoResult updateLottoResult(LottoResult lottoResult) {
        this.lottoResult = lottoResult;
        return lottoResult;
    }

    public LottoResult getLottoResult() {
        return lottoResult;
    }
}
