package it.romanowski.controller.view;

import java.util.Set;

public class OrderCreationView {
    private String buyersEmail;
    private Set<Long> productsIds;

    public String getBuyersEmail() {
        return buyersEmail;
    }

    public void setBuyersEmail(String buyersEmail) {
        this.buyersEmail = buyersEmail;
    }

    public Set<Long> getProductsIds() {
        return productsIds;
    }

    public void setProductsIds(Set<Long> productsIds) {
        this.productsIds = productsIds;
    }
}
