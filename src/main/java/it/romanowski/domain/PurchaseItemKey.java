package it.romanowski.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Embeddable
public class PurchaseItemKey implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    public PurchaseItemKey() {
    }

    public PurchaseItemKey(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseItemKey that = (PurchaseItemKey) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PurchaseItemKey.class.getSimpleName() + "[", "]")
                .add("orderId=" + orderId)
                .add("productId=" + productId)
                .toString();
    }
}
