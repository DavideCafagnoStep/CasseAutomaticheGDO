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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.step.casseAutomatiche.models.Product;
import it.step.casseAutomatiche.services.ProductService;
import it.step.casseAutomatiche.utility.LambdaCleanerId;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getAllProduct(){
		logger.info("ProductController getAllProduct Method");
		ResponseEntity<List<Product>> res = null;
		List<Product> lst = service.getAll();
		
		if(lst.size() == 0) {
			logger.info("ProductController getAllProduct NOT FOUND, result: "+ lst);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("ProductController GOOD getAllProduct result\n"+ lst);
			res= ResponseEntity.ok(lst);
		}
				
		return res;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getOneProductById(@PathVariable(value = "id") Integer id){
		logger.info("ProductController getOneProductById Method");
		logger.info("ProductController getOneProductById Given Id: "+ id);
		ResponseEntity<Product> res = null;
		Product pro = service.getOneById(id);
		
		if(pro == null) {
			logger.info("ProductController getOneProductById NOT FOUND, result: "+ pro);
			res= ResponseEntity.notFound().build();
		}else {
			logger.info("ProductController GOOD getOneProductById result\n"+ pro);
			res= ResponseEntity.ok(pro);
		}
				
		return res;
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> saveOneProduct(@RequestBody Product proInput){
		logger.info("ProductController saveOneProduct Method");
		logger.info("ProductController saveOneProduct Given Product \n "+ proInput);
		proInput.setId(null);
		ResponseEntity<Product> res = null;
		try {
			Product proSaved = service.saveProduct(proInput);
			if(proSaved == null) {
				logger.info("ProductController saveOneProduct BADREQUEST, result: "+ proSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(proSaved);
				logger.info("ProductController GOOD saveOneProduct result\n"+ proSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("ProductController BAD saveOneProduct error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@PostMapping(value = "/multisave", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> saveAllProduct(@RequestBody List<Product> prolstInput){
		logger.info("ProductController saveOneProduct Method");
		logger.info("ProductController saveOneProduct Given Product \n "+ prolstInput);
		
		//Lambda Expression
		//prolstInput.forEach( p -> p.setId(null));
		LambdaCleanerId <List<Product>> cleaner = (lst) -> {lst.forEach(p->p.setId(null));};
		cleaner.clean(prolstInput);
		System.out.println(prolstInput);
		
		ResponseEntity<List<Product>> res = null;
		try {
			List<Product> proSaved = service.saveAllProduct(prolstInput);
			if(proSaved == null) {
				logger.info("ProductController saveOneProduct BADREQUEST, result: "+ proSaved);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(proSaved);
				logger.info("ProductController GOOD saveOneProduct result\n"+ proSaved);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("ProductController BAD saveOneProduct error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	
	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateOneProduct(@RequestBody Product proInput){
		logger.info("ProductController updateOneProduct Method");
		logger.info("ProductController updateOneProduct Given Product \n "+ proInput);
		ResponseEntity<Product> res = null;
		try {
			Product proUpdated = service.updateProduct(proInput);
			if(proUpdated == null) {
				logger.info("ProductController updateOneProduct BADREQUEST, result: "+ proUpdated);
				res= ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}else {
				res= ResponseEntity.ok(proUpdated);
				logger.info("ProductController GOOD updateOneProduct result\n"+ proUpdated);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("ProductController BAD updateOneProduct error: "+ e.getMessage());
			res = ResponseEntity.internalServerError().build();
		}
				
		return res;
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Product> deleteProductById(@PathVariable(value="id") Integer id){
		logger.info("ProductController deleteProductById Method");
		logger.info("ProductController deleteProductById Given ID: "+ id);
		ResponseEntity<Product> res = null;
		Product productToDelete = service.getOneById(id);
		if(productToDelete == null) {
			logger.info("ProductController deleteProductById NOT FOUND, result: "+ productToDelete);
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("ProductController deleteProductById Product FOUND, result: "+ productToDelete);
			try {
				if(service.deleteProductById(id)) {
					logger.info("PriceController deleteProductById Product Deleted");
					res= ResponseEntity.ok(productToDelete);
				}else {
					logger.info("PriceController deleteProductById Product Not Deleted");
					res= ResponseEntity.badRequest().build();
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("ProductController deleteProductById Product Not Deleted, error: "+e.getMessage());
				res= ResponseEntity.internalServerError().build();
			}
			
		}
		
		return res;
	}

}
