package it.romanowski.domain;

import java.math.BigDecimal;

public class PurchaseItemFactory {
    public static PurchaseItem createPurchaseItem(long id, String name, BigDecimal price) {
        Order order = OrderFactory.createOrder(id);
        Product product = ProductFactory.createProduct(id);
        product.setPrice(price);
        product.setName(name);

        return new PurchaseItem(order, product);
    }

    public static PurchaseItem createPurchaseItem(BigDecimal price) {
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setOrderPrice(price);
        return purchaseItem;
    }
}
