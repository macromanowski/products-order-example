package it.romanowski.repositories;

import it.romanowski.domain.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}
