package it.romanowski.services;

import it.romanowski.domain.Order;
import it.romanowski.domain.OrderCreationRequest;
import it.romanowski.domain.OrderFactory;
import it.romanowski.domain.Product;
import it.romanowski.domain.ProductFactory;
import it.romanowski.repositories.OrderRepository;
import it.romanowski.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceIntegrationTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static boolean DB_INITIALIZED = false;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService service;

    @BeforeEach
    public void initDb() {
        if (!DB_INITIALIZED) {
            Product productOne = ProductFactory.createProduct(1);
            productOne.setName("Product One");
            productOne.setPrice(BigDecimal.valueOf(12.34));

            Product productTwo = ProductFactory.createProduct(2);
            productTwo.setName("Product Two");
            productTwo.setPrice(BigDecimal.valueOf(56.78));

            saveToDb(productOne, productTwo);

            Order orderOne = OrderFactory.createOrder(1);
            Order orderTwo = OrderFactory.createOrder(2);
            Order orderThree = OrderFactory.createOrder(3);

            saveToDb(orderOne, orderTwo, orderThree);

            orderOne.setOrderTime(LocalDateTime.parse("2020-02-02 13:00:00", FORMATTER));
            orderTwo.setOrderTime(LocalDateTime.parse("2020-02-02 14:00:00", FORMATTER));
            orderThree.setOrderTime(LocalDateTime.parse("2020-02-02 15:00:00", FORMATTER));

            saveToDb(orderOne, orderTwo, orderThree);

            DB_INITIALIZED = true;
        }
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void findOrdersByDate_betweenDates_shouldReturn2Records() {
        //arrange
        LocalDateTime from = LocalDateTime.parse("2020-02-02 12:00:00", FORMATTER);
        LocalDateTime to = LocalDateTime.parse("2020-02-02 14:01:00", FORMATTER);

        //act
        Collection<Order> result = service.getAll(from, to);

        //assert
        assertThat(result).extracting(Order::getId).containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void findOrdersByDate_afterDateOnly_shouldReturn2Records() {
        //arrange
        LocalDateTime from = LocalDateTime.parse("2020-02-02 13:30:00", FORMATTER);

        //act
        Collection<Order> result = service.getAll(from, null);

        //assert
        assertThat(result).extracting(Order::getId).containsExactlyInAnyOrder(2L, 3L);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void findOrdersByDate_beforeDateOnly_shouldReturn1Records() {
        //arrange
        LocalDateTime to = LocalDateTime.parse("2020-02-02 13:30:00", FORMATTER);

        //act
        Collection<Order> result = service.getAll(null, to);

        //assert
        assertThat(result).extracting(Order::getId).containsExactlyInAnyOrder(1L);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void createOrder_withProperData_shouldCreateOne() {
        //arrange
        OrderCreationRequest request = new OrderCreationRequest();
        request.setProductsIds(Set.of(1L, 2L));

        //act
        Order result = service.createOrder(request);

        //assert
        assertThat(orderRepository.findById(result.getId())).isPresent();
    }

    private void saveToDb(Order... orders) {
        for (Order order : orders) {
            orderRepository.save(order);
        }
    }

    private void saveToDb(Product... products) {
        for (Product product : products) {
            productRepository.save(product);
        }
    }
}