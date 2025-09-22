package lotto.lottoticket.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LottoTicket {

    private static final long UNCHECKED = 0L;
    private static final long COUNTED = 1L;

    private static final int MATCHES_THRESHOLD = 5;

    private enum State {PURCHASE_COMPLETE, UNCLASSIFIED_SECOND_AND_THIRD_TICKET_LEFTOVER}

    private final List<Lotto> unclassified;


    /**
     * 분기(맞춘 갯수 5개)에 따른 비동기 처리 후
     * 불변 객체를 반환하기 위한 생성자
     */
    private LottoTicket(List<Lotto> unclassified) {
        this.unclassified = List.copyOf(unclassified);
    }

    public static LottoTicket buying(long purchaseCount) {
        List<Lotto> unclassified = Stream.generate(Lotto::quickPicks)
                .limit(purchaseCount)
                .toList();

        return new LottoTicket(unclassified);
    }

    public LottoTicket leftOver() {
        return new LottoTicket(this.unclassified);
    }

    public Entry<Long, Stream<String>> purchasedLottoLog() {
        return Map.entry(unclassifiedLottoCount(), purchasedLotto());
    }
//
//    public Entry<Long, String> purchasedLottoLog() {
//        return Map.entry(unclassifiedLottoCount(), purchasedLotto());
//    }
//    private String purchasedLotto() {
//        return unclassified.stream()
//                .map(Lotto::numbers)
//                .map(Object::toString)
//                .collect(Collectors.joining(System.lineSeparator()));
//    }

    private Stream<String> purchasedLotto() {
        return unclassified.stream()
                .map(Lotto::numbers)
                .map(Object::toString);
    }

    private long unclassifiedLottoCount() {
        return unclassified.size();
    }

    /**
     * 분기에 해당하는 맞춘 갯수(5개 맞춘 로또)가 없을 경우 보너스 번호를 입력 받고
     * 검증을 마치는 동시에 추가 계산 이벤트를 스킵한다.
     * -> 비동기적으로 계산해 둔 계산 내역을 바로 출력하도록 이벤트 조정 필요
     */
    public boolean hasUnclassifiedLotto() {
        return !unclassified.isEmpty();
    }

    public Map<Integer, Long> matchedTable(Collection<Integer> winningNumbers) {
        Map<Integer, Long> table = new HashMap<>();
        classifyNonBonusMatches(winningNumbers, table);
        return table;
    }

    public Map<Boolean,Long> matchedTable(int bonusNumber) {
        Map<Boolean, Long> table = new HashMap<>();
        classifyBonusMatches(bonusNumber, table);
        return table;
    }

    private void classifyNonBonusMatches(Collection<Integer> winningNumbers, Map<Integer, Long> table) {
        Iterator<Lotto> iterator = unclassified.iterator();
        while (iterator.hasNext()) {
            Lotto lotto = iterator.next();
            int matchedCount = lotto.matchedCount(winningNumbers);

            if (matchedCount != MATCHES_THRESHOLD) {
                table.put(matchedCount, table.getOrDefault(matchedCount, UNCHECKED) + COUNTED);
                iterator.remove();
            }
        }
    }

    private void classifyBonusMatches(Integer bonusNumber, Map<Boolean, Long> table) {
        Iterator<Lotto> iterator = unclassified.iterator();
        while (iterator.hasNext()) {
            Lotto lotto = iterator.next();
            boolean hasBonus = lotto.isContained(bonusNumber);

            table.put(hasBonus, table.getOrDefault(hasBonus, UNCHECKED) + COUNTED);
            iterator.remove();
        }
    }

//
//    private final List<Lotto> tickets;
//    private final WinningCondition winningCondition;
//
//    private LottoTicket(long purchaseCount) {
//        this.tickets = buy(purchaseCount);
//        this.winningCondition = WinningCondition.enterWinningNumber("test");
//    }
//
//    public static LottoTicket of(long purchaseCount) {
//        return new LottoTicket(purchaseCount);
//    }
//
//    private List<Lotto> buy (long purchaseCount) {
//        List<Lotto> lottoTickets = new ArrayList<>();
//
//        for (int i = 0; i < purchaseCount ; i++) {
//            lottoTickets.add(Lotto.quickPicks());
//        }
//        return lottoTickets;
//    }
//
//    private String print() {
//        StringBuilder buffer = new StringBuilder();
//        for (Lotto ticket : tickets) {
//            buffer.append(ticket.numbers()).append(System.lineSeparator());
//        }
//        return buffer.toString().trim();
//    }
//
//    public Entry<Long, String> gameCountAndNumbers() {
//        return Map.entry(gameCount(),print());
//    }
//
//    private long gameCount() {
//        return tickets.stream().count();
//    }
//
//    public Entry<Integer, Boolean> getResult(Lotto lotto) {
//        return null;
//    }
//
//    public List<Integer> correctCounts() {
//        List<Integer> correctCounts = new ArrayList<>();
//        for (Lotto ticket : tickets) {
//            ticket.matchedCount(winningCondition.)
//        }
//    }
}
