package it.romanowski.services;

import it.romanowski.domain.Order;
import it.romanowski.domain.OrderCreationRequest;
import it.romanowski.domain.Product;
import it.romanowski.domain.ProductFactory;
import it.romanowski.exceptions.ResourceNotFoundException;
import it.romanowski.repositories.OrderRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void getById_notFound_shouldThrow() {
        //arrange
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        //act
        Throwable thrown = catchThrowable(() -> orderService.getById(123));

        //assert
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    public void getAllById_notFound_shouldThrow() {
        //arrange
        when(orderRepository.findAll(any())).thenReturn(Lists.newArrayList(new Order(), new Order()));

        //act
        Collection<Order> result = orderService.getAll(null, null);

        //assert
        assertThat(result).hasSize(2);
    }

    @Test
    public void createOrder_withUnknownProducts_shouldThrow() {
        //arrange
        when(productService.getAllByIds(any()))
                .thenReturn(Lists.newArrayList(ProductFactory.createProduct(1), ProductFactory.createProduct(2)));

        OrderCreationRequest request = new OrderCreationRequest();
        request.setProductsIds(Set.of(1L, 2L, 3L));

        //act
        Throwable thrown = catchThrowable(() -> orderService.createOrder(request));

        //assert
        assertThat(thrown).isInstanceOf(Exception.class);
    }
}