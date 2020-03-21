package it.romanowski.services;

import it.romanowski.domain.Product;
import it.romanowski.exceptions.ResourceNotFoundException;
import it.romanowski.repositories.ProductRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(long id) {
        Optional<Product> possibleProduct = productRepository.findById(id);
        return possibleProduct.orElseThrow(ResourceNotFoundException::new);
    }

    public Collection<Product> getAll() {
        List<Product> products = new ArrayList<>();
        CollectionUtils.addAll(products, productRepository.findAll());
        return products;
    }

    public Collection<Product> getAllByIds(Set<Long> productIds) {
        List<Product> products = new ArrayList<>();
        CollectionUtils.addAll(products, productRepository.findAllById(productIds));
        return products;
    }


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(long productId, Product updateRequest) {
        Product productToUpdate = productRepository.findById(productId)
                .orElseThrow(ResourceNotFoundException::new);

        productToUpdate.setName(updateRequest.getName());
        productToUpdate.setPrice(updateRequest.getPrice());

        return productRepository.save(productToUpdate);
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}
