package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.models.Department;
import it.step.casseAutomatiche.repository.DepartmentRepo;
import it.step.casseAutomatiche.services.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentRepo repo;

	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<Department> getAll() {
		logger.info("DepartmentServiceImpl getAll Method"); 
		List<Department> res = repo.findAll();
		if(res.size() == 0)
			logger.info("DepartmentServiceImpl NOT FOUND result: "+ res); 
		else
			logger.info("DepartmentServiceImpl GOOD getAll \n" + res);
		
		return res;
	}

	@Override
	public Department getOneById(Integer id) {
		logger.info("DepartmentServiceImpl getOneById Method");
		logger.info("DepartmentServiceImpl getOneById - Given ID: " + id);
		Department res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("DepartmentServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("DepartmentServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public Department saveDepartment(Department department) {
		logger.info("DepartmentServiceImpl saveDepartment Method");
		logger.info("DepartmentServiceImpl saveDepartment - Given Department: " + department);
		Department res = null;
		try {
			res = repo.save(department);
			logger.info("DepartmentServiceImpl GOOD getOneById \n" + res);
		}catch(Exception e ) {
			logger.info("DepartmentServiceImpl BAD getOneById message: "+ e.getMessage());
			e.printStackTrace();
		}
		
		return res;
	}

}
