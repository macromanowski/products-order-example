package it.romanowski.controller;

import it.romanowski.controller.view.OrderCreationView;
import it.romanowski.controller.view.OrderView;
import it.romanowski.controller.view.ProductUpsertRequest;
import it.romanowski.converters.OrderConverter;
import it.romanowski.converters.ProductConverter;
import it.romanowski.domain.Order;
import it.romanowski.domain.OrderCreationRequest;
import it.romanowski.domain.Product;
import it.romanowski.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Collection<OrderView> getOrders(@RequestParam(value = "from", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromTime,
                                           @RequestParam(value = "to", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toTime) {
        Collection<Order> products = orderService.getAll(fromTime, toTime);

        return products.stream()
                .map(OrderConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderView getOrder(@PathVariable long id) {
        Order order = orderService.getById(id);
        return OrderConverter.convert(order);
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@RequestBody OrderCreationView request, UriComponentsBuilder ucb) {
        OrderCreationRequest creationRequest = OrderConverter.convert(request);
        Order createdOrder = orderService.createOrder(creationRequest);

        URI location = ucb.path("/orders/{id}").buildAndExpand(createdOrder.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
