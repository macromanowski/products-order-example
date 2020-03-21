package it.romanowski.controller.view;

import java.math.BigDecimal;

public class OrderProductView {
    private String name;
    private BigDecimal orderPrice;

    public OrderProductView(String name, BigDecimal orderPrice) {
        this.name = name;
        this.orderPrice = orderPrice;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }
}
