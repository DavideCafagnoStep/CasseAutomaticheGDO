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

import it.step.casseAutomatiche.models.Sell;
import it.step.casseAutomatiche.services.SellService;
import it.step.casseAutomatiche.utility.LambdaCleanerId;

@RestController
@RequestMapping(value = "/sells")
public class SellController {
	
	@Autowired
	private SellService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Sell>> getAllSell(){
		logger.info("SellController getAllSell Method");
		ResponseEntity<List<Sell>> res = null;
		List<Sell> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("SellController getAllSell NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("SellController GOOD getAllSell result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sell> getOneSellById(@PathVariable(value = "id") Integer id){
		logger.info("SellController getOneSellById Method");
		logger.info("SellController getOneSellById Given Id: "+ id);
		ResponseEntity<Sell> res = null;
		Sell sell = service.getOneById(id);
		
		if(sell == null) {
			logger.info("SellController getOneSellById NOT FOUND, result: "+ sell);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("SellController GOOD getOneSellById result\n"+ sell);
			res= ResponseEntity.ok(sell);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sell> saveOneSell(@RequestBody Sell sellInput){
		logger.info("SellController saveOneSell Method");
		logger.info("SellController saveOneSell Given Sell \n "+ sellInput);
		sellInput.setId(null);
		ResponseEntity<Sell> res = null;
		try {
			Sell sellSaved = service.saveSell(sellInput);
			if(sellSaved == null) {
				logger.info("SellController saveOneSell BADREQUEST, result: "+ sellSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(sellSaved);
				logger.info("SellController GOOD saveOneSell result\n"+ sellSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("SellController BAD saveOneSell error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@PostMapping(value = "/multisave", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Sell>> saveAllSell(@RequestBody List<Sell> sellsInput){
		logger.info("SellController saveOneSell Method");
		logger.info("SellController saveOneSell Given Sell \n "+ sellsInput);
		
		//Lambda Expression
		LambdaCleanerId<List<Sell>> cleaner = (lst) -> lst.forEach((s) -> s.setId(null)); 
		cleaner.clean(sellsInput);
		ResponseEntity<List<Sell>> res = null;
		try {
			List<Sell> sellSaved = service.saveAllSell(sellsInput);
			if(sellSaved.size() == 0 ) {
				logger.info("SellController saveOneSell BADREQUEST, result: "+ sellSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(sellSaved);
				logger.info("SellController GOOD saveOneSell result\n"+ sellSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("SellController BAD saveOneSell error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Sell> deleteSellById(@PathVariable(value="id") Integer id){
		logger.info("SellController deleteSellById Method");
		logger.info("SellController deleteSellById Given ID: "+ id);
		ResponseEntity<Sell> res = null;
		Sell sellToDelete = service.getOneById(id);
		if(sellToDelete == null) {
			logger.info("SellController deleteSellById NOT FOUND, result: "+ sellToDelete);
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("SellController deleteSellById Sell FOUND, result: "+ sellToDelete);
			try {
				if(service.deleteSellById(id)) {
					logger.info("PriceController deleteSellById Sell Deleted");
					res= ResponseEntity.ok(sellToDelete);
				}else {
					logger.info("PriceController deleteSellById Sell Not Deleted");
					res= ResponseEntity.badRequest().build();
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("SellController deleteSellById Sell Not Deleted, error: "+e.getMessage());
				res= ResponseEntity.internalServerError().build();
			}
			
		}
		
		return res;
	}
}
