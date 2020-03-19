package it.romanowski.controller;

import it.romanowski.controller.view.ProductUpsertRequest;
import it.romanowski.controller.view.ProductView;
import it.romanowski.converters.ProductConverter;
import it.romanowski.domain.Product;
import it.romanowski.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Collection<ProductView> getProducts() {
        Collection<Product> products = productService.getAll();

        return products.stream()
                .map(ProductConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductView getProduct(@PathVariable long id) {
        Product product = productService.getById(id);
        return ProductConverter.convert(product);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody ProductUpsertRequest request, UriComponentsBuilder ucb) {
        Product creationRequest = ProductConverter.convert(request);
        Product createdProduct = productService.saveProduct(creationRequest);

        URI location = ucb.path("/products/{id}").buildAndExpand(createdProduct.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody ProductUpsertRequest request) {
        Product updateRequest = ProductConverter.convert(request);
        productService.updateProduct(id, updateRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }
}
