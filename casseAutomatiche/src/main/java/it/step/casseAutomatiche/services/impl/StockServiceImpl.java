package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.repository.StockRepo;
import it.step.casseAutomatiche.services.StockService;
import it.step.casseAutomatiche.models.Stock;

@Service
@Transactional
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepo repo;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	@Override
	public List<Stock> getAll() {
		logger.info("StockServiceImpl getAll Method"); 
		List<Stock> res = repo.findAll();
		if(res.size() == 0)
			logger.info("StockServiceImpl NOT FOUND result: "+ res); 
		else
			logger.info("StockServiceImpl GOOD getAll \n" + res);
		
		return res;
	}

	@Override
	public Stock getOneById(Integer id) {
		logger.info("StockServiceImpl getOneById Method");
		logger.info("StockServiceImpl getOneById - Given ID: " + id);
		Stock res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("StockServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("StockServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public Stock saveStock(Stock stock) {
		logger.info("StockServiceImpl saveStock Method");
		logger.info("StockServiceImpl saveStock - Given Data:\n" + stock);
		try{
			Stock res = repo.save(stock);
			logger.info("StockServiceImpl GOOD saveStock \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("StockServiceImpl BAD saveStock result: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public Stock updateStock(Stock stock) {
		logger.info("StockServiceImpl updateStock Method");
		logger.info("StockServiceImpl updateStock - Given Data:\n" + stock);
		Stock stockToUpdate = repo.findById(stock.getId()).orElse(null); 
		if(stockToUpdate == null) {
			logger.info("StockServiceImpl BAD updateStock - Stock not Found - result: " + stockToUpdate);
			return null;
		}else {
			logger.info("StockServiceImpl GOOD updateStock 1 - Initial Data:\n" + stockToUpdate);
			stockToUpdate.setNum(stock.getNum());
			stockToUpdate.setDate(stock.getDate());
			logger.info("StockServiceImpl GOOD updateStock 2 - Updated Data:\n" + stockToUpdate);
			return stockToUpdate;
		}
	}

	@Override
	public Boolean deleteStockById(Integer id) {
		logger.info("StockServiceImpl deleteStockById Method");
		logger.info("StockServiceImpl deleteStockById - Given ID: " + id);
		Stock res = repo.findById(id).orElse(null);
		if(res == null) {
			logger.info("StockServiceImpl BAD deleteStockById - Stock not found: " + res);
			return false;
		}else {
			logger.info("StockServiceImpl deleteStockById 1 - Stock found:\n" + res);
			try{
				repo.delete(res);
				logger.info("StockServiceImpl GOOD deleteStockById 2 - Stock Deleted");
				return true;
			}catch(Exception e ) {
				e.printStackTrace();
				logger.info("StockServiceImpl BAD deleteStockById 2 - Stock Not Deleted: " + e.getMessage());
				return false;
			}
			
		}
	}

}
