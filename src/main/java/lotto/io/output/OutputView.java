package lotto.io.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class OutputView {

    private static final String BUY_FORMAT = "%d개를 구매했습니다.";

//    public static void buyLotto(Entry<Long, String> lottoTicketStatus) {
//        String purchasedLog = System.lineSeparator() + String.format(BUY_FORMAT, lottoTicketStatus.getKey());
//
//        System.out.println(purchasedLog);
//        System.out.println(lottoTicketStatus.getValue());
//    }
    public static void buyLotto(Entry<Long, Stream<String>> lottoTicketStatus) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        writeProcess(bw,lottoTicketStatus);
        print(bw);
    }

    private static void writeProcess(BufferedWriter bw,
                                     Entry<Long, Stream<String>> lottoTicketStatus) throws IOException {

        writePurchasedLottoCount(bw,lottoTicketStatus);
        lottoTicketStatus.getValue().forEach(lotto -> writeLottoLineFrom(bw, lotto));
    }

    private static void writePurchasedLottoCount(BufferedWriter bw,
                                                 Entry<Long, Stream<String>> lottoTicketStatus) throws IOException {

        bw.write(String.format(BUY_FORMAT, lottoTicketStatus.getKey()));
        bw.newLine();
    }

    private static void writeLottoLineFrom(BufferedWriter bw, String lotto) {
        try {
            bw.write(lotto);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing lotto: " + lotto);
        }
    }

    private static void print(BufferedWriter bw) throws IOException {
        bw.flush();
        bw.close();
    }
}
