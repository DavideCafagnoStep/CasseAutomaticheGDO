package it.step.casseAutomatiche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.step.casseAutomatiche.models.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}
