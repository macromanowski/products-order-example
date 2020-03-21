package it.romanowski.services;

import it.romanowski.domain.Order;
import it.romanowski.domain.OrderCreationRequest;
import it.romanowski.domain.Product;
import it.romanowski.exceptions.ResourceNotFoundException;
import it.romanowski.repositories.OrderRepository;
import it.romanowski.repositories.specs.OrderSpec;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order getById(long id) {
        Optional<Order> possibleProduct = orderRepository.findById(id);
        return possibleProduct.orElseThrow(ResourceNotFoundException::new);
    }

    public Collection<Order> getAll(LocalDateTime after, LocalDateTime before) {
        List<Order> products = new ArrayList<>();

        Specification<Order> spec = Specification.where(OrderSpec.dateAfter(after)).and(OrderSpec.dateBefore(before));
        CollectionUtils.addAll(products, orderRepository.findAll(spec));

        return products;
    }

    @Transactional
    public Order createOrder(OrderCreationRequest request) {
        Collection<Product> productsToAdd = productService.getAllByIds(request.getProductsIds());

        Set<Long> foundIds = productsToAdd.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        if (!CollectionUtils.containsAll(foundIds, request.getProductsIds())) {
            throw new IllegalArgumentException("Not all products were found for given set!");
        }

        Order order = new Order();
        order.setBuyersEmail(request.getBuyersEmail());
        Order savedOrder = orderRepository.save(order);

        savedOrder.addProducts(productsToAdd);

        return savedOrder;
    }
}
