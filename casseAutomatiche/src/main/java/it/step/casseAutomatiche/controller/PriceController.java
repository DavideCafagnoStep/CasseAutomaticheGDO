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

import it.step.casseAutomatiche.models.Price;
import it.step.casseAutomatiche.services.PriceService;

@RestController
@RequestMapping(value = "/prices")
public class PriceController {
	
	@Autowired
	private PriceService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Price>> getAllPrice(){
		logger.info("PriceController getAllPrice Method");
		ResponseEntity<List<Price>> res = null;
		List<Price> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("PriceController getAllPrice NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("PriceController GOOD getAllPrice result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Price> getOnePriceById(@PathVariable(value = "id") Integer id){
		logger.info("PriceController getOnePriceById Method");
		logger.info("PriceController getOnePriceById Given Id: "+ id);
		ResponseEntity<Price> res = null;
		Price price = service.getOneById(id);
		
		if(price == null) {
			logger.info("PriceController getOnePriceById NOT FOUND, result: "+ price);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("PriceController GOOD getOnePriceById result\n"+ price);
			res= ResponseEntity.ok(price);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Price> saveOnePrice(@RequestBody Price priceInput){
		logger.info("PriceController saveOnePrice Method");
		logger.info("PriceController saveOnePrice Given Price \n "+ priceInput);
		priceInput.setId(null);
		ResponseEntity<Price> res = null;
		try {
			Price priceSaved = service.savePrice(priceInput);
			if(priceSaved == null) {
				logger.info("PriceController saveOnePrice BADREQUEST, result: "+ priceSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(priceSaved);
				logger.info("PriceController GOOD saveOnePrice result\n"+ priceSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("PriceController BAD saveOnePrice error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Price> deletePriceById(@PathVariable(value="id") Integer id){
		logger.info("PriceController deletePriceById Method");
		logger.info("PriceController deletePriceById Given ID: "+ id);
		ResponseEntity<Price> res = null;
		Price priceToDelete = service.getOneById(id);
		if(priceToDelete == null) {
			logger.info("PriceController deletePriceById NOT FOUND, result: "+ priceToDelete);
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("PriceController deletePriceById Price FOUND, result: "+ priceToDelete);
			try {
				if(service.deletePriceById(id)) {
					logger.info("PriceController deletePriceById Price Deleted");
					res= ResponseEntity.ok(priceToDelete);
				}else {
					logger.info("PriceController deletePriceById Price Not Deleted");
					res= ResponseEntity.badRequest().build();
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("PriceController deletePriceById Price Not Deleted, error: "+e.getMessage());
				res= ResponseEntity.internalServerError().build();
			}
			
		}
		
		return res;
	}
}
