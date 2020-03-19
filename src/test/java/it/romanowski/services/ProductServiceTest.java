package it.romanowski.services;

import it.romanowski.domain.Product;
import it.romanowski.domain.ProductFactory;
import it.romanowski.exceptions.ResourceNotFoundException;
import it.romanowski.repositories.ProductRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void getById_notFound_shouldThrow() {
        //arrange
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        //act
        Throwable thrown = catchThrowable(() -> productService.getById(123));

        //assert
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    public void getAllById_notFound_shouldThrow() {
        //arrange
        when(productRepository.findAll()).thenReturn(Lists.newArrayList(new Product(), new Product()));

        //act
        Collection<Product> result = productService.getAll();

        //assert
        assertThat(result).hasSize(2);
    }

    @Test
    public void updateProduct_validProduct_shouldUpdate() {
        //arrange
        Product product = ProductFactory.createProduct(1);
        product.setName("oldName");

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        Product updateRequest = new Product();
        updateRequest.setName("newName");

        doAnswer(invocation -> {
            return invocation.getArgument(0);
        }).when(productRepository).save(any());

        //act
        Product updatedProduct = productService.updateProduct(1, updateRequest);

        //assert
        assertThat(updatedProduct.getId()).isEqualTo(1);
        assertThat(updatedProduct.getName()).isEqualTo("newName");
    }

    @Test
    public void updateProduct_notFound_shouldThrow() {
        //arrange
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        //act
        Throwable thrown = catchThrowable(() -> productService.updateProduct(123, new Product()));

        //assert
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
    }
}