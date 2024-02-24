package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.models.TypeOfMeasure;
import it.step.casseAutomatiche.repository.TypeOfMeasureRepo;
import it.step.casseAutomatiche.services.TypeOfMeasureService;

@Service
@Transactional
public class TypeOfMeasureServiceImpl implements TypeOfMeasureService {

	@Autowired
	private TypeOfMeasureRepo repo;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public List<TypeOfMeasure> getAll() {
		logger.info("TypeOfMeasureServiceImpl getAll Method"); 
		List<TypeOfMeasure> res = repo.findAll();
		if(res.size() == 0)
			logger.info("TypeOfMeasureServiceImpl NOT FOUND result: "+ res); 
		else
			logger.info("TypeOfMeasureServiceImpl GOOD getAll \n" + res);
		
		return res;	}

	@Override
	public TypeOfMeasure getOneById(Integer id) {
		logger.info("TypeOfMeasureServiceImpl getOneById Method");
		logger.info("TypeOfMeasureServiceImpl getOneById - Given ID: " + id);
		TypeOfMeasure res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("TypeOfMeasureServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("TypeOfMeasureServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public TypeOfMeasure saveTypeOfMeasure(TypeOfMeasure typeOfMeasure) {
		logger.info("TypeOfMeasureServiceImpl saveTypeOfMeasure Method");
		logger.info("TypeOfMeasureServiceImpl saveTypeOfMeasure - Given TypeOfMeasure: " + typeOfMeasure);
		TypeOfMeasure res = null;
		try {
			res = repo.save(typeOfMeasure);
			logger.info("TypeOfMeasureServiceImpl GOOD getOneById \n" + res);
		}catch(Exception e ) {
			logger.info("TypeOfMeasureServiceImpl BAD getOneById message: "+ e.getMessage());
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public Boolean deleteTypeOfMeasureById(Integer id) {
		logger.info("TypeOfMeasureServiceImpl deleteTypeOfMeasureById Method");
		logger.info("TypeOfMeasureServiceImpl deleteTypeOfMeasureById - Given ID: " + id);
		TypeOfMeasure res = repo.findById(id).orElse(null);
		if(res == null) {
			logger.info("TypeOfMeasureServiceImpl BAD deleteTypeOfMeasureById - Stock not found: " + res);
			return false;
		}else {
			logger.info("TypeOfMeasureServiceImpl deleteTypeOfMeasureById 1 - Stock found:\n" + res);
			try{
				repo.delete(res);
				logger.info("TypeOfMeasureServiceImpl GOOD deleteTypeOfMeasureById 2 - Stock Deleted");
				return true;
			}catch(Exception e ) {
				e.printStackTrace();
				logger.info("TypeOfMeasureServiceImpl BAD deleteTypeOfMeasureById 2 - Stock Not Deleted: " + e.getMessage());
				return false;
			}
			
		}
	}

}
