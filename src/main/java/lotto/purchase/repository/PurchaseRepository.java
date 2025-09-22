package lotto.purchase.repository;

import lotto.purchase.domain.Purchase;

public class PurchaseRepository {

    private Purchase purchase;

    public void setup(Purchase purchase) {
        this.purchase = purchase;
    }

    public Purchase getPurchase() {
        return this.purchase;
    }

    public void clear() {
        purchase = null;
    }
}
