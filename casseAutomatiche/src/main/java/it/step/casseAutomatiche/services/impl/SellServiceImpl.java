package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.repository.SellRepo;
import it.step.casseAutomatiche.services.SellService;
import it.step.casseAutomatiche.models.Sell;

@Service
@Transactional
public class SellServiceImpl implements SellService {
	
	@Autowired
	private SellRepo repo;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Sell> getAll() {
		logger.info("SellServiceImpl getAll Method"); 
		List<Sell> res = repo.findAll();
		if(res.size() == 0)
			logger.info("SellServiceImpl NOT FOUND result: "+ res); 
		else
			logger.info("SellServiceImpl GOOD getAll \n" + res);
		
		return res;
	}

	@Override
	public Sell getOneById(Integer id) {
		logger.info("SellServiceImpl getOneById Method");
		logger.info("SellServiceImpl getOneById - Given ID: " + id);
		Sell res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("SellServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("SellServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public Sell saveSell(Sell sell) {
		logger.info("SellServiceImpl saveSell Method");
		logger.info("SellServiceImpl saveSell - Given Data:\n" + sell);
		try{
			Sell res = repo.save(sell);
			logger.info("SellServiceImpl GOOD saveSell \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("SellServiceImpl BAD saveSell result: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public List<Sell> saveAllSell(List<Sell> sells) {
		logger.info("SellServiceImpl saveAllSell Method");
		logger.info("SellServiceImpl saveAllSell - Given Data:\n" + sells);
		try{
			List<Sell> res = repo.saveAll(sells);
			logger.info("SellServiceImpl GOOD saveAllSell \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("SellServiceImpl BAD saveAllSell result: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public Sell updateSell(Sell sell) {
		logger.info("SellServiceImpl updateSell Method");
		logger.info("SellServiceImpl updateSell - Given Data:\n" + sell);
		Sell sellToUpdate = repo.findById(sell.getId()).orElse(null);
		if(sellToUpdate == null) {
			logger.info("SellServiceImpl BAD updateSell - Sell not Found - result: " + sellToUpdate);
			return null;
		}else {
			logger.info("SellServiceImpl GOOD updateSell 1 - Initial Data:\n" + sellToUpdate);
			//Update Logic
			logger.info("SellServiceImpl GOOD updateSell 2 - Updated Data:\n" + sellToUpdate);
			return sellToUpdate;
		}
	}

	@Override
	public Boolean deleteSellById(Integer id) {
		logger.info("SellServiceImpl deleteSellById Method");
		logger.info("SellServiceImpl deleteSellById - Given ID: " + id);
		Sell res = repo.findById(id).orElse(null);
		if(res == null) {
			logger.info("SellServiceImpl BAD deleteSellById - Sell not found: " + res);
			return false;
		}else {
			logger.info("SellServiceImpl deleteSellById 1 - Sell found:\n" + res);
			try{
				repo.delete(res);
				logger.info("SellServiceImpl GOOD deleteSellById 2 - Sell Deleted");
				return false;
			}catch(Exception e ) {
				e.printStackTrace();
				logger.info("SellServiceImpl BAD deleteSellById 2 - Sell Not Deleted: " + e.getMessage());
				return true;
			}
			
		}
	}



}
