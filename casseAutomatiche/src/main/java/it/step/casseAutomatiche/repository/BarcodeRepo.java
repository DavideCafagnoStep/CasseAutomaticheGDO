package it.step.casseAutomatiche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.step.casseAutomatiche.models.Barcode;

public interface BarcodeRepo extends JpaRepository<Barcode, Integer> {

}
