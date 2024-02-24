package it.step.casseAutomatiche.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.step.casseAutomatiche.models.TypeOfMeasure;
import it.step.casseAutomatiche.services.TypeOfMeasureService;

@RestController
@RequestMapping(value="/typeofmeasures")
public class TypeOfMeasureController {
	
	@Autowired
	private TypeOfMeasureService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TypeOfMeasure>> getAllTypeOfMeasure(){
		logger.info("TypeOfMeasureController getAllTypeOfMeasure Method");
		ResponseEntity<List<TypeOfMeasure>> res = null;
		List<TypeOfMeasure> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("TypeOfMeasureController getAllTypeOfMeasure NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("TypeOfMeasureController GOOD getAllTypeOfMeasure result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TypeOfMeasure> getOneTypeOfMeasureById(@PathVariable(value = "id") Integer id){
		logger.info("TypeOfMeasureController getOneTypeOfMeasureById Method");
		logger.info("TypeOfMeasureController getOneTypeOfMeasureById Given Id: "+ id);
		ResponseEntity<TypeOfMeasure> res = null;
		TypeOfMeasure typeOfMeasure = service.getOneById(id);
		
		if(typeOfMeasure == null) {
			logger.info("TypeOfMeasureController getOneTypeOfMeasureById NOT FOUND, result: "+ typeOfMeasure);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("TypeOfMeasureController GOOD getOneTypeOfMeasureById result\n"+ typeOfMeasure);
			res= ResponseEntity.ok(typeOfMeasure);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TypeOfMeasure> saveOneTypeOfMeasure(@RequestBody TypeOfMeasure typeOfMeasureInput){
		logger.info("TypeOfMeasureController saveOneTypeOfMeasure Method");
		logger.info("TypeOfMeasureController saveOneTypeOfMeasure Given TypeOfMeasure \n "+ typeOfMeasureInput);
		typeOfMeasureInput.setId(null);
		ResponseEntity<TypeOfMeasure> res = null;
		try {
			TypeOfMeasure typeOfMeasureSaved = service.saveTypeOfMeasure(typeOfMeasureInput);
			if(typeOfMeasureSaved == null) {
				logger.info("TypeOfMeasureController saveOneTypeOfMeasure BADREQUEST, result: "+ typeOfMeasureSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(typeOfMeasureSaved);
				logger.info("TypeOfMeasureController GOOD saveOneTypeOfMeasure result\n"+ typeOfMeasureSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("TypeOfMeasureController BAD saveOneTypeOfMeasure error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<TypeOfMeasure> deleteTypeOfMeasureById(@PathVariable(value="id") Integer id){
		logger.info("TypeOfMeasureController deleteTypeOfMeasureById Method");
		logger.info("TypeOfMeasureController deleteTypeOfMeasureById Given ID: "+ id);
		ResponseEntity<TypeOfMeasure> res = null;
		TypeOfMeasure typeOfMeasureToDelete = service.getOneById(id);
		if(typeOfMeasureToDelete == null) {
			logger.info("TypeOfMeasureController deleteTypeOfMeasureById NOT FOUND, result: "+ typeOfMeasureToDelete);
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("TypeOfMeasureController deleteTypeOfMeasureById TypeOfMeasure FOUND, result: "+ typeOfMeasureToDelete);
			try {
				if(service.deleteTypeOfMeasureById(id)) {
					logger.info("PriceController deleteTypeOfMeasureById TypeOfMeasure Deleted");
					res= ResponseEntity.ok(typeOfMeasureToDelete);
				}else {
					logger.info("PriceController deleteTypeOfMeasureById TypeOfMeasure Not Deleted");
					res= ResponseEntity.badRequest().build();
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("TypeOfMeasureController deleteTypeOfMeasureById TypeOfMeasure Not Deleted, error: "+e.getMessage());
				res= ResponseEntity.internalServerError().build();
			}
			
		}
		
		return res;
	}

}
