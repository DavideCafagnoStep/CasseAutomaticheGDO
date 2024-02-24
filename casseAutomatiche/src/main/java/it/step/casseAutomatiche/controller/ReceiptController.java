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

import it.step.casseAutomatiche.models.Receipt;
import it.step.casseAutomatiche.services.ReceiptService;

@RestController
@RequestMapping(value = "/receipts")
public class ReceiptController {
	
	@Autowired
	private ReceiptService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Receipt>> getAllReceipt(){
		logger.info("ReceiptController getAllReceipt Method");
		ResponseEntity<List<Receipt>> res = null;
		List<Receipt> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("ReceiptController getAllReceipt NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("ReceiptController GOOD getAllReceipt result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Receipt> getOneReceiptById(@PathVariable(value = "id") Integer id){
		logger.info("ReceiptController getOneReceiptById Method");
		logger.info("ReceiptController getOneReceiptById Given Id: "+ id);
		ResponseEntity<Receipt> res = null;
		Receipt receipt = service.getOneById(id);
		
		if(receipt == null) {
			logger.info("ReceiptController getOneReceiptById NOT FOUND, result: "+ receipt);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("ReceiptController GOOD getOneReceiptById result\n"+ receipt);
			res= ResponseEntity.ok(receipt);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Receipt> saveOneReceipt(@RequestBody Receipt receiptInput){
		logger.info("ReceiptController saveOneReceipt Method");
		logger.info("ReceiptController saveOneReceipt Given Receipt \n "+ receiptInput);
		receiptInput.setId(null);
		ResponseEntity<Receipt> res = null;
		try {
			Receipt receiptSaved = service.saveReceipt(receiptInput);
			if(receiptSaved == null) {
				logger.info("ReceiptController saveOneReceipt BADREQUEST, result: "+ receiptSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(receiptSaved);
				logger.info("ReceiptController GOOD saveOneReceipt result\n"+ receiptSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("ReceiptController BAD saveOneReceipt error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Receipt> deleteReceiptById(@PathVariable(value="id") Integer id){
		logger.info("ReceiptController deleteReceiptById Method");
		logger.info("ReceiptController deleteReceiptById Given ID: "+ id);
		ResponseEntity<Receipt> res = null;
		Receipt receiptToDelete = service.getOneById(id);
		if(receiptToDelete == null) {
			logger.info("ReceiptController deleteReceiptById NOT FOUND, result: "+ receiptToDelete);
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("ReceiptController deleteReceiptById Receipt FOUND, result: "+ receiptToDelete);
			try {
				if(service.deleteReceiptById(id)) {
					logger.info("PriceController deleteReceiptById Receipt Deleted");
					res= ResponseEntity.ok(receiptToDelete);
				}else {
					logger.info("PriceController deleteReceiptById Receipt Not Deleted");
					res= ResponseEntity.badRequest().build();
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("ReceiptController deleteReceiptById Receipt Not Deleted, error: "+e.getMessage());
				res= ResponseEntity.internalServerError().build();
			}
			
		}
		
		return res;
	}

}
