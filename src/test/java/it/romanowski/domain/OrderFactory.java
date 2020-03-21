package it.romanowski.domain;

import org.assertj.core.util.Lists;

public class OrderFactory {
    public static Order createOrderWithPurchaseItem(long id) {
        Order order = new Order();
        order.id = id;

        order.addProducts(Lists.newArrayList(ProductFactory.createProduct(id)));

        return order;
    }

    public static Order createOrder(long id) {
        Order order = new Order();
        order.id = id;

        return order;
    }
}
