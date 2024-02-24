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

import it.step.casseAutomatiche.models.Stock;
import it.step.casseAutomatiche.services.StockService;

@RestController
@RequestMapping(value = "/stocks")
public class StockController {
	
	@Autowired
	private StockService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Stock>> getAllStock(){
		logger.info("StockController getAllStock Method");
		ResponseEntity<List<Stock>> res = null;
		List<Stock> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("StockController getAllStock NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("StockController GOOD getAllStock result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Stock> getOneStockById(@PathVariable(value = "id") Integer id){
		logger.info("StockController getOneStockById Method");
		logger.info("StockController getOneStockById Given Id: "+ id);
		ResponseEntity<Stock> res = null;
		Stock stock = service.getOneById(id);
		
		if(stock == null) {
			logger.info("StockController getOneStockById NOT FOUND, result: "+ stock);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("StockController GOOD getOneStockById result\n"+ stock);
			res= ResponseEntity.ok(stock);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Stock> saveOneStock(@RequestBody Stock stockInput){
		logger.info("StockController saveOneStock Method");
		logger.info("StockController saveOneStock Given Stock \n "+ stockInput);
		stockInput.setId(null);
		ResponseEntity<Stock> res = null;
		try {
			Stock stockSaved = service.saveStock(stockInput);
			if(stockSaved == null) {
				logger.info("StockController saveOneStock BADREQUEST, result: "+ stockSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(stockSaved);
				logger.info("StockController GOOD saveOneStock result\n"+ stockSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("StockController BAD saveOneStock error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Stock> deleteStockById(@PathVariable(value="id") Integer id){
		logger.info("StockController deleteStockById Method");
		logger.info("StockController deleteStockById Given ID: "+ id);
		ResponseEntity<Stock> res = null;
		Stock stockToDelete = service.getOneById(id);
		if(stockToDelete == null) {
			logger.info("StockController deleteStockById NOT FOUND, result: "+ stockToDelete);
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("StockController deleteStockById Stock FOUND, result: "+ stockToDelete);
			try {
				if(service.deleteStockById(id)) {
					logger.info("PriceController deleteStockById Stock Deleted");
					res= ResponseEntity.ok(stockToDelete);
				}else {
					logger.info("PriceController deleteStockById Stock Not Deleted");
					res= ResponseEntity.badRequest().build();
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("StockController deleteStockById Stock Not Deleted, error: "+e.getMessage());
				res= ResponseEntity.internalServerError().build();
			}
			
		}
		
		return res;
	}

}
