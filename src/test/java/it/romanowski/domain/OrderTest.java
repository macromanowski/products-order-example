package it.romanowski.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    @Test
    public void getFullPrice_properData_shouldCalculateProperly() {
        //arrange
        PurchaseItem firstItem = PurchaseItemFactory.createPurchaseItem(BigDecimal.valueOf(12.34));
        PurchaseItem secondItem = PurchaseItemFactory.createPurchaseItem(BigDecimal.valueOf(12.34));

        Order order = new Order();
        order.setPurchaseItems(Set.of(firstItem, secondItem));
        //act
        BigDecimal fullPrice = order.getFullPrice();

        //assert
        assertThat(fullPrice).isEqualTo(BigDecimal.valueOf(24.68));
    }

    @Test
    public void addProducts_properData_shouldAddNewItems() {
        //arrange
        Product product = ProductFactory.createProduct(1);

        Order order = new Order();
        //act
        order.addProducts(Set.of(product));

        //assert
        assertThat(order.getPurchaseItems()).hasSize(1);
    }
}