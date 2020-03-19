package it.romanowski.domain;

public class ProductFactory {
    public static Product createProduct(long id) {
        Product product = new Product();
        product.id = id;

        return product;
    }
}
