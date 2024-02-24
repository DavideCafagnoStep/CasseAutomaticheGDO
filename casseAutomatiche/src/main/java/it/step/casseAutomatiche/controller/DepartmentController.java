package it.step.casseAutomatiche.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.step.casseAutomatiche.models.Department;
import it.step.casseAutomatiche.services.DepartmentService;

@RestController
@RequestMapping(value="/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Department>> getAllDepartment(){
		logger.info("DepartmentController getAllDepartment Method");
		ResponseEntity<List<Department>> res = null;
		List<Department> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("DepartmentController getAllDepartment NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("DepartmentController GOOD getAllDepartment result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Department> getOneDepartmentById(@PathVariable(value = "id") Integer id){
		logger.info("DepartmentController getOneDepartmentById Method");
		logger.info("DepartmentController getOneDepartmentById Given Id: "+ id);
		ResponseEntity<Department> res = null;
		Department department = service.getOneById(id);
		
		if(department == null) {
			logger.info("DepartmentController getOneDepartmentById NOT FOUND, result: "+ department);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("DepartmentController GOOD getOneDepartmentById result\n"+ department);
			res= ResponseEntity.ok(department);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Department> saveOneDepartment(@RequestBody Department departmentInput){
		logger.info("DepartmentController saveOneDepartment Method");
		logger.info("DepartmentController saveOneDepartment Given Department \n "+ departmentInput);
		departmentInput.setId(null);
		ResponseEntity<Department> res = null;
		try {
			Department departmentSaved = service.saveDepartment(departmentInput);
			if(departmentSaved == null) {
				logger.info("DepartmentController saveOneDepartment BADREQUEST, result: "+ departmentSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(departmentSaved);
				logger.info("DepartmentController GOOD saveOneDepartment result\n"+ departmentSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("DepartmentController BAD saveOneDepartment error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}

}
