package it.romanowski.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //it's quite easy to be asked by sequential asking and probably sth different should be used
    long id;

    private String buyersEmail;

    @CreationTimestamp
    private LocalDateTime orderTime;

    @OneToMany(mappedBy = "orderId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<PurchaseItem> purchaseItems = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getBuyersEmail() {
        return buyersEmail;
    }

    public void setBuyersEmail(String buyersEmail) {
        this.buyersEmail = buyersEmail;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    @Transient
    public BigDecimal getFullPrice() {
        return Stream.ofNullable(purchaseItems)
                .flatMap(Collection::stream)
                .map(PurchaseItem::getOrderPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(Set<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    public void addProducts(Collection<Product> products) {
        products.stream()
                .map(product -> new PurchaseItem(this, product))
                .forEach(purchaseItems::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(buyersEmail, order.buyersEmail) &&
                Objects.equals(orderTime, order.orderTime) &&
                Objects.equals(purchaseItems, order.purchaseItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyersEmail, orderTime, purchaseItems);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("buyersEmail='" + buyersEmail + "'")
                .add("orderTime=" + orderTime)
                .add("purchaseItems=" + purchaseItems)
                .toString();
    }
}
