package it.romanowski.controller.view;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderView {
    private long id;
    private String buyersEmail;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime orderTime;
    private BigDecimal fullPrice;
    private List<OrderProductView> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public List<OrderProductView> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProductView> products) {
        this.products = products;
    }
}
