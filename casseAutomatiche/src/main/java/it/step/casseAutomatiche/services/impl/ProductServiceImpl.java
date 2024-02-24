package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.repository.ProductRepo;
import it.step.casseAutomatiche.services.ProductService;
import it.step.casseAutomatiche.models.Product;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo repo;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Product> getAll() {
		logger.info("ProductServiceImpl getAll Method"); 
		List<Product> res = repo.findAll();
		if(res.size() == 0)
			logger.info("ProductServiceImpl NOT FOUND getAll result: "+ res); 
		else
			logger.info("ProductServiceImpl GOOD getAll \n" + res);
		
		return res;
	}

	@Override
	public Product getOneById(Integer id) {
		logger.info("ProductServiceImpl getOneById Method"); 
		logger.info("ProductServiceImpl getOneById Given ID: " + id); 
		Product res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("ProductServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("ProductServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public Product saveProduct(Product product) {
		logger.info("ProductServiceImpl saveProduct Method"); 
		logger.info("ProductServiceImpl saveProduct Given product:\n" + product); 
		try{
			Product res = repo.save(product);
			logger.info("ProductServiceImpl GOOD saveProduct \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("ProductServiceImpl BAD saveProduct result: "+ e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Product> saveAllProduct(List<Product> products) {
		logger.info("ProductServiceImpl saveAllProduct Method"); 
		logger.info("ProductServiceImpl saveAllProduct Given products:\n" + products); 
		try{
			List<Product> res = repo.saveAll(products);
			logger.info("ProductServiceImpl GOOD saveAllProduct \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("ProductServiceImpl BAD saveAllProduct result: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public Product updateProduct(Product product) {
		logger.info("ProductServiceImpl updateProduct Method"); 
		logger.info("ProductServiceImpl updateProduct Given product:\n" + product); 
		Product productToUpdate = repo.findById(product.getId()).orElse(null);
		if(productToUpdate == null) {
			logger.info("ProductServiceImpl BAD updateProduct - Product not Found - result: " + productToUpdate);
			return null;
		}else {
			logger.info("ProductServiceImpl GOOD updateProduct 1 - Initial Data:\n" + productToUpdate);
			productToUpdate.setDescription(product.getDescription());
			productToUpdate.setDepartment(product.getDepartment());
			productToUpdate.setWeight(product.getWeight());
			productToUpdate.setTypeOfMeasure(product.getTypeOfMeasure());
			logger.info("ProductServiceImpl GOOD updateProduct 2 - Updated Data:\n" + productToUpdate);
			return productToUpdate;
		}
	}

	@Override
	public Boolean deleteProductById(Integer id) {
		logger.info("ProductServiceImpl deleteProductById Method"); 
		logger.info("ProductServiceImpl deleteProductById Given ID: " + id);
		Product res = repo.findById(id).orElse(null);
		if(res == null) {
			logger.info("ProductServiceImpl BAD deleteProductById - Product not found: " + res);
			return false;
		}else {
			logger.info("ProductServiceImpl deleteProductById 1 - Product found:\n" + res);
			try{
				repo.delete(res);
				logger.info("ProductServiceImpl GOOD deleteProductById 2 - Product Deleted");
				return true;
			}catch(Exception e ) {
				e.printStackTrace();
				logger.info("ProductServiceImpl BAD deleteProductById 2 - Product Not Deleted: " + e.getMessage());
				return false;
			}
			
		}
	}


}
