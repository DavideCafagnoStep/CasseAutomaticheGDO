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

import it.step.casseAutomatiche.models.Barcode;
import it.step.casseAutomatiche.services.BarcodeService;

@RestController
@RequestMapping(value = "/barcodes")
public class BarcodeController {
	
	@Autowired
	private BarcodeService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Barcode>> getAllBarcode(){
		logger.info("BarcodeController getAllBarcode Method");
		ResponseEntity<List<Barcode>> res = null;
		List<Barcode> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("BarcodeController getAllBarcode NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("BarcodeController GOOD getAllBarcode result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Barcode> getOneBarcodeById(@PathVariable(value = "id") Integer id){
		logger.info("BarcodeController getOneBarcodeById Method");
		logger.info("BarcodeController getOneBarcodeById Given Id: "+ id);
		ResponseEntity<Barcode> res = null;
		Barcode barcode = service.getOneById(id);
		
		if(barcode == null) {
			logger.info("BarcodeController getOneBarcodeById NOT FOUND, result: "+ barcode);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("BarcodeController GOOD getOneBarcodeById result\n"+ barcode);
			res= ResponseEntity.ok(barcode);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Barcode> saveOneBarcode(@RequestBody Barcode barcodeInput){
		logger.info("BarcodeController saveOneBarcode Method");
		logger.info("BarcodeController saveOneBarcode Given Barcode \n "+ barcodeInput);
		barcodeInput.setId(null);
		ResponseEntity<Barcode> res = null;
		try {
			Barcode barcodeSaved = service.saveBarcode(barcodeInput);
			if(barcodeSaved == null) {
				logger.info("BarcodeController saveOneBarcode BADREQUEST, result: "+ barcodeSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(barcodeSaved);
				logger.info("BarcodeController GOOD saveOneBarcode result\n"+ barcodeSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("BarcodeController BAD saveOneBarcode error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Barcode> deleteBarcodeById(@PathVariable(value="id") Integer id){
		logger.info("BarcodeController deleteBarcodeById Method");
		logger.info("BarcodeController deleteBarcodeById Given ID: "+ id);
		ResponseEntity<Barcode> res = null;
		Barcode barcodeToDelete = service.getOneById(id);
		if(barcodeToDelete == null) {
			logger.info("BarcodeController deleteBarcodeById NOT FOUND, result: "+ barcodeToDelete);
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("BarcodeController deleteBarcodeById Barcode FOUND, result: "+ barcodeToDelete);
			try {
				if(service.deleteBarcodeById(id)) {
					logger.info("PriceController deleteBarcodeById Barcode Deleted");
					res= ResponseEntity.ok(barcodeToDelete);
				}else {
					logger.info("PriceController deleteBarcodeById Barcode Not Deleted");
					res= ResponseEntity.badRequest().build();
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("BarcodeController deleteBarcodeById Barcode Not Deleted, error: "+e.getMessage());
				res= ResponseEntity.internalServerError().build();
			}
			
		}
		
		return res;
	}

}
