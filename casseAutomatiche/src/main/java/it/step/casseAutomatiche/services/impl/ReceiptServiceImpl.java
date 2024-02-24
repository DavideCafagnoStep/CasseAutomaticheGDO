package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.repository.ReceiptRepo;
import it.step.casseAutomatiche.services.ReceiptService;
import it.step.casseAutomatiche.models.Receipt;

@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

	@Autowired
	private ReceiptRepo repo;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Receipt> getAll() {
		logger.info("ReceiptServiceImpl getAll Method"); 
		List<Receipt> res = repo.findAll();
		if(res.size() == 0)
			logger.info("ReceiptServiceImpl NOT FOUND result: "+ res); 
		else
			logger.info("ReceiptServiceImpl GOOD getAll \n" + res);
		
		return res;
	}

	@Override
	public Receipt getOneById(Integer id) {
		logger.info("ReceiptServiceImpl getOneById Method");
		logger.info("ReceiptServiceImpl getOneById - Given ID: " + id);
		Receipt res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("ReceiptServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("ReceiptServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public Receipt saveReceipt(Receipt Receipt) {
		logger.info("ReceiptServiceImpl saveReceipt Method");
		logger.info("ReceiptServiceImpl saveReceipt - Given Data:\n" + Receipt);
		try{
			Receipt res = repo.save(Receipt);
			logger.info("ReceiptServiceImpl GOOD saveReceipt \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("ReceiptServiceImpl BAD saveReceipt result: "+ e.getMessage());
			return null;
		}
	}

	@Override
	public Receipt updateReceipt(Receipt receipt) {
		logger.info("ReceiptServiceImpl updateReceipt Method");
		logger.info("ReceiptServiceImpl updateReceipt - Given Data:\n" + receipt);
		Receipt receiptToUpdate = repo.findById(receipt.getId()).orElse(null);
		if(receiptToUpdate == null) {
			logger.info("ReceiptServiceImpl BAD updateReceipt - Receipt not Found - result: " + receiptToUpdate);
			return null;
		}else {
			logger.info("ReceiptServiceImpl GOOD updateReceipt 1 - Initial Data:\n" + receiptToUpdate);
			receiptToUpdate.setDate(receipt.getDate());
			receiptToUpdate.setTotal(receipt.getTotal());
			logger.info("ReceiptServiceImpl GOOD updateReceipt 2 - Updated Data:\n" + receiptToUpdate);
			return receiptToUpdate;
		}
	}

	@Override
	public Boolean deleteReceiptById(Integer id) {
		logger.info("ReceiptServiceImpl deleteReceiptById Method");
		logger.info("ReceiptServiceImpl deleteReceiptById - Given ID: " + id);
		Receipt res = repo.findById(id).orElse(null);
		if(res == null) {
			logger.info("ReceiptServiceImpl BAD deleteReceiptById - Receipt not found: " + res);
			return false;
		}else {
			logger.info("ReceiptServiceImpl deleteReceiptById 1 - Receipt found:\n" + res);
			try{
				repo.delete(res);
				logger.info("ReceiptServiceImpl GOOD deleteReceiptById 2 - Receipt Deleted");
				return true;
			}catch(Exception e ) {
				e.printStackTrace();
				logger.info("ReceiptServiceImpl BAD deleteReceiptById 2 - Receipt Not Deleted: " + e.getMessage());
				return false;
			}
			
		}
		
	}

	
}
