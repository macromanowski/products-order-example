package it.romanowski.converters;

import it.romanowski.controller.view.ProductUpsertRequest;
import it.romanowski.controller.view.ProductView;
import it.romanowski.domain.Product;
import it.romanowski.domain.ProductFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductConverterTest {

    @Test
    public void productToProductView_properObjectConversion_shouldPass() {
        //arrange
        Product product = ProductFactory.createProduct(1);
        product.setName("example");
        product.setPrice(BigDecimal.ONE);

        //act
        ProductView result = ProductConverter.convert(product);

        //assert
        Assertions.assertThat(result.getId()).isEqualTo(1);
        Assertions.assertThat(result.getName()).isEqualTo("example");
        Assertions.assertThat(result.getPrice()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void productRequestToProduct_properObjectConversion_shouldPass() {
        //arrange
        ProductUpsertRequest request = new ProductUpsertRequest();
        request.setName("example");
        request.setPrice(BigDecimal.ONE);

        //act
        Product result = ProductConverter.convert(request);

        //assert
        Assertions.assertThat(result.getName()).isEqualTo("example");
        Assertions.assertThat(result.getPrice()).isEqualTo(BigDecimal.ONE);
    }
}