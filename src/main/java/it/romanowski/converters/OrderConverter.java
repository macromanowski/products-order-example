package it.romanowski.converters;

import it.romanowski.controller.view.*;
import it.romanowski.domain.Order;
import it.romanowski.domain.OrderCreationRequest;
import it.romanowski.domain.PurchaseItem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderView convert(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order cannot be null!");
        }

        OrderView orderView = new OrderView();
        orderView.setId(order.getId());
        orderView.setBuyersEmail(order.getBuyersEmail());
        orderView.setFullPrice(order.getFullPrice());
        orderView.setOrderTime(order.getOrderTime());
        List<OrderProductView> productViews = order.getPurchaseItems().stream()
                .map(OrderConverter::convert)
                .sorted(Comparator.comparing(OrderProductView::getName))
                .collect(Collectors.toList());

        orderView.setProducts(productViews);

        return orderView;
    }

    static OrderProductView convert(PurchaseItem purchaseItem) {
        return new OrderProductView(purchaseItem.getProductId().getName(), purchaseItem.getOrderPrice());
    }

    public static OrderCreationRequest convert(OrderCreationView creationView) {
        if (creationView == null) {
            throw new IllegalArgumentException("order cannot be null!");
        }
        OrderCreationRequest orderCreationRequest = new OrderCreationRequest();
        orderCreationRequest.setBuyersEmail(creationView.getBuyersEmail());
        orderCreationRequest.setProductsIds(creationView.getProductsIds());

        return orderCreationRequest;
    }
}
