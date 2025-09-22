package lotto.lottoticket.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Collection;
import java.util.List;
import utils.StatelessGlobalLottoRulesValidator;

public class Lotto {

    private static final int NON_MATCHED = 0;
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private Lotto() {
        this.numbers = quickPick();
    }

    // 자동 뽑기 구매시 생성용
    public static Lotto quickPicks() {
        return new Lotto();
    }

    private void validate(List<Integer> numbers) {
        requireCorrectBallCount(numbers);
        requireUniqueNumbers(numbers);
        requireNumbersInRange(numbers);
        requireNumbersInRange(numbers);
    }

    private void requireNumbersInRange(List<Integer> numbers) {
        numbers.forEach(this::requireNumberInRange);
    }

    private void requireNumberInRange(int number) {
        StatelessGlobalLottoRulesValidator.requireLottoNumberRange(number);
    }

    private void requireCorrectBallCount(List<Integer> numbers) {
        StatelessGlobalLottoRulesValidator.requireCorrectBallCount(numbers);
    }

    private void requireUniqueNumbers(List<Integer> numbers) {
        StatelessGlobalLottoRulesValidator.requireUniqueNumbers(numbers);
    }

    private List<Integer> quickPick() {
        return Randoms.pickUniqueNumbersInRange(
                LottoRules.MIN_NUMBER.value(),
                LottoRules.MAX_NUMBER.value(),
                LottoRules.LOTTO_BALL_COUNT.value()).stream().sorted().toList();
    }

    public List<Integer> numbers() {
        return List.copyOf(this.numbers);
    }

    public int matchedCount(Collection<Integer> winningSet) {
       return (int) this.numbers.stream()
               .filter(winningSet::contains)
               .count();
    }

    public boolean isContained(int number) {
        return this.numbers.contains(number);
    }

    private enum LottoRules {
        MIN_NUMBER(1),
        MAX_NUMBER(45),
        LOTTO_BALL_COUNT(6);

        private final int value;

        LottoRules(int value) {
            this.value = value;
        }

        int value() {
            return value;
        }
    }
}
