package it.romanowski.converters;

import it.romanowski.controller.view.OrderCreationView;
import it.romanowski.controller.view.OrderProductView;
import it.romanowski.controller.view.OrderView;
import it.romanowski.domain.Order;
import it.romanowski.domain.OrderCreationRequest;
import it.romanowski.domain.OrderFactory;
import it.romanowski.domain.PurchaseItem;
import it.romanowski.domain.PurchaseItemFactory;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OrderConverterTest {

    @Test
    public void orderToOrderView_properObjectConversion_shouldPass() {
        //arrange
        LocalDateTime orderTime = LocalDateTime.now();

        Order order = OrderFactory.createOrderWithPurchaseItem(1);
        order.setBuyersEmail("email@com.example");
        order.setOrderTime(orderTime);

        //act
        OrderView result = OrderConverter.convert(order);

        //assert
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getBuyersEmail()).isEqualTo("email@com.example");
        assertThat(result.getOrderTime()).isEqualTo(orderTime);
        assertThat(result.getProducts()).hasSize(1);
    }

    @Test
    public void purchaseItemToOrderProductView_properObjectConversion_shouldPass() {
        //arrange
        PurchaseItem purchaseItem = PurchaseItemFactory.createPurchaseItem(1, "productName", BigDecimal.valueOf(12.32));

        //act
        OrderProductView result = OrderConverter.convert(purchaseItem);

        //assert
        assertThat(result.getName()).isEqualTo("productName");
        assertThat(result.getOrderPrice()).isEqualTo(BigDecimal.valueOf(12.32));
    }

    @Test
    public void orderCreationViewToOrderCreationRequest_properObjectConversion_shouldPass() {
        //arrange
        OrderCreationView orderCreationView = new OrderCreationView();
        orderCreationView.setBuyersEmail("email@com.example");
        orderCreationView.setProductsIds(Sets.newLinkedHashSet(1L, 2L));


        //act
        OrderCreationRequest result = OrderConverter.convert(orderCreationView);

        //assert
        assertThat(result.getBuyersEmail()).isEqualTo("email@com.example");
        assertThat(result.getProductsIds()).containsExactlyInAnyOrder(1L, 2L);
    }
}