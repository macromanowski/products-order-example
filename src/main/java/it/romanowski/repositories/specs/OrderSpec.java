package it.romanowski.repositories.specs;

import it.romanowski.domain.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpec {
    public static Specification<Order> dateAfter(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
            date == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("orderTime"), date);
    }

    public static Specification<Order> dateBefore(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("orderTime"), date);
    }
}
