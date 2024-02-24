package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.Department;

public interface DepartmentService {

	public List<Department> getAll();
	public Department getOneById(Integer id);
	public Department saveDepartment(Department department);
}
