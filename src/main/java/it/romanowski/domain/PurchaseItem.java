package it.romanowski.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class PurchaseItem implements Serializable  {

    @EmbeddedId
    private PurchaseItemKey id;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order orderId;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product productId;

    private BigDecimal orderPrice;

    public PurchaseItem() {
    }

    public PurchaseItem(Order orderId, Product product) {
        this.id = new PurchaseItemKey(orderId.getId(), product.getId());
        this.orderId = orderId;
        this.productId = product;
        this.orderPrice = product.getPrice();
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
