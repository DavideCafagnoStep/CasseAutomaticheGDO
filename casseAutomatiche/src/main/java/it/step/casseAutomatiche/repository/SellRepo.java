package it.step.casseAutomatiche.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import it.step.casseAutomatiche.models.Sell;

public interface SellRepo extends JpaRepository<Sell, Integer> {
	

}
