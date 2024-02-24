package it.step.casseAutomatiche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.step.casseAutomatiche.models.Stock;

public interface StockRepo extends JpaRepository<Stock, Integer> {

}
