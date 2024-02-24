package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.repository.PriceRepo;
import it.step.casseAutomatiche.services.PriceService;
import it.step.casseAutomatiche.models.Price;
import it.step.casseAutomatiche.models.Product;

@Service
@Transactional
public class PriceServiceImpl implements PriceService {
	
	@Autowired
	private PriceRepo repo;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	@Override
	public List<Price> getAll() {
		logger.info("PriceServiceImpl getAll Method"); 
		List<Price> res = repo.findAll();
		if(res.size() == 0)
			logger.info("PriceServiceImpl NOT FOUND result: "+ res); 
		else
			logger.info("PriceServiceImpl GOOD getAll \n" + res);
		
		return res;
	}

	@Override
	public Price getOneById(Integer id) {
		logger.info("PriceServiceImpl getOneById Method");
		logger.info("PriceServiceImpl getOneById - Given ID: " + id);
		Price res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("PriceServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("PriceServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public Price savePrice(Price price) {
		logger.info("PriceServiceImpl savePrice Method");
		logger.info("PriceServiceImpl savePrice - Given Data:\n" + price);
		try{
			Price res = repo.save(price);
			logger.info("PriceServiceImpl GOOD savePrice \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("PriceServiceImpl BAD savePrice result: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public Price updatePrice(Price price) {
		logger.info("PriceServiceImpl updatePrice Method");
		logger.info("PriceServiceImpl updatePrice - Given Data:\n" + price);
		Price priceToUpdate = repo.findById(price.getId()).orElse(null);
		if(priceToUpdate == null) {
			logger.info("PriceServiceImpl BAD updatePrice - Price not Found - result: " + priceToUpdate);
			return null;
		}else {
			logger.info("PriceServiceImpl GOOD updatePrice 1 - Initial Data:\n" + priceToUpdate);
			priceToUpdate.setValue(price.getValue());
			
			logger.info("PriceServiceImpl GOOD updatePrice 2 - Updated Data:\n" + priceToUpdate);
			return priceToUpdate;
		}
	}

	@Override
	public Boolean deletePriceById(Integer id) {
		logger.info("PriceServiceImpl deletePriceById Method");
		logger.info("PriceServiceImpl deletePriceById - Given ID: " + id);
		Price res = repo.findById(id).orElse(null);
		if(res == null) {
			logger.info("PriceServiceImpl BAD deletePriceById - Price not found: " + res);
			return false;
		}else {
			logger.info("PriceServiceImpl deletePriceById 1 - Price found:\n" + res);
			try{
				repo.delete(res);
				logger.info("PriceServiceImpl GOOD deletePriceById 2 - Price Deleted");
				return true;
			}catch(Exception e ) {
				e.printStackTrace();
				logger.info("PriceServiceImpl BAD deletePriceById 2 - Price Not Deleted: " + e.getMessage());
				return false;
			}
			
		}
	}

	@Override
	public Price getOneByProduct(Product product) {
		logger.info("PriceServiceImpl getOneById Method");
		logger.info("PriceServiceImpl getOneById - Given Product: ", product);
		Price res = repo.findOneByProduct(product).orElse(null);
		if(res == null)
			logger.info("PriceServiceImpl NOT FOUND getOneById result: ", res); 
		else
			logger.info("PriceServiceImpl GOOD getOneById \n", res);
		
		return res;
	}

}
