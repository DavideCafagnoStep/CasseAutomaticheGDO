package it.step.casseAutomatiche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.step.casseAutomatiche.models.Receipt;

public interface ReceiptRepo extends JpaRepository<Receipt, Integer> {

}
