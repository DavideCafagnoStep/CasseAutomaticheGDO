package it.step.casseAutomatiche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.step.casseAutomatiche.models.Price;
import it.step.casseAutomatiche.models.Product;

public interface PriceRepo extends JpaRepository<Price, Integer> {

	Optional<Price> findOneByProduct(Product product);

}
