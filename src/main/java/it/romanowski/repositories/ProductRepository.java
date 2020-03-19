package it.romanowski.repositories;

import it.romanowski.domain.Product;
import org.springframework.data.repository.CrudRepository;

//we might use PagingAndSortingRepository here
public interface ProductRepository extends CrudRepository<Product, Long> {
}
