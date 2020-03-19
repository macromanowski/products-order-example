package it.romanowski.converters;

import it.romanowski.controller.view.ProductUpsertRequest;
import it.romanowski.controller.view.ProductView;
import it.romanowski.domain.Product;

public class ProductConverter {
    public static final ProductView convert(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null!");
        }
        return new ProductView(product.getId(), product.getName(), product.getPrice());
    }

    public static final Product convert(ProductUpsertRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null!");
        }

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return product;
    }
}
