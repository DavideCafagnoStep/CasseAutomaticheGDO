package it.step.casseAutomatiche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.step.casseAutomatiche.models.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
