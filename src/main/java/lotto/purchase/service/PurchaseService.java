package lotto.purchase.service;

import java.math.BigDecimal;
import lotto.purchase.domain.Purchase;
import lotto.purchase.repository.PurchaseRepository;

public class PurchaseService {

    public final PurchaseRepository purchaseRepository;

    public PurchaseService() {
        this.purchaseRepository = new PurchaseRepository();
    }

    public void createPurchase(String amount) {
        this.purchaseRepository.setup(Purchase.of(amount));
    }

    public long purchaseCount() {
        Purchase purchase = purchaseRepository.getPurchase();
        return purchase.quantity();
    }

    public BigDecimal purchasedMoney() {
        Purchase purchase = purchaseRepository.getPurchase();
        return purchase.purchaseMade();
    }

    public void clearPurchase() {
        purchaseRepository.clear();
    }
}
