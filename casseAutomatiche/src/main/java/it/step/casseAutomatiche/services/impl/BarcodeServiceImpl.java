package it.step.casseAutomatiche.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.step.casseAutomatiche.repository.BarcodeRepo;
import it.step.casseAutomatiche.services.BarcodeService;
import it.step.casseAutomatiche.models.Barcode;

@Service
@Transactional
public class BarcodeServiceImpl implements BarcodeService {
	
	@Autowired
	private BarcodeRepo repo;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());

	@Override 
	public List<Barcode> getAll() {
		logger.info("BarcodeServiceImpl getAll Method"); 
		List<Barcode> res = repo.findAll();
		if(res.size() == 0)
			logger.info("BarcodeServiceImpl NOT FOUND result: "+ res); 
		else
			logger.info("BarcodeServiceImpl GOOD getAll \n" + res);
		
		return res;
	}

	@Override
	public Barcode getOneById(Integer id) {
		logger.info("BarcodeServiceImpl getOneById Method");
		logger.info("BarcodeServiceImpl getOneById - Given ID: " + id);
		Barcode res = repo.findById(id).orElse(null);
		if(res == null)
			logger.info("BarcodeServiceImpl NOT FOUND getOneById result: "+ res); 
		else
			logger.info("BarcodeServiceImpl GOOD getOneById \n" + res);
		
		return res;
	}

	@Override
	public Barcode saveBarcode(Barcode barcode) {
		logger.info("BarcodeServiceImpl saveBarcode Method");
		logger.info("BarcodeServiceImpl saveBarcode - Given Data:\n" + barcode);
		try{
			Barcode res = repo.save(barcode);
			logger.info("BarcodeServiceImpl GOOD saveBarcode \n" + res);
			return res;
			
		}catch (Exception e){
			e.printStackTrace();
			logger.info("BarcodeServiceImpl BAD saveBarcode result: "+ e.getMessage());
			return null;
		}
		
	}

	@Override
	public Barcode updateBarcode(Barcode barcode) {
		logger.info("BarcodeServiceImpl updateBarcode Method");
		logger.info("BarcodeServiceImpl updateBarcode - Given Data:\n" + barcode);
		Barcode barcodeToUpdate = repo.findById(barcode.getId()).orElse(null);
		if(barcodeToUpdate == null) {
			logger.info("BarcodeServiceImpl BAD updateBarcode - Barcode not Found - result: " + barcodeToUpdate);
			return null;
		}else {
			logger.info("BarcodeServiceImpl GOOD updateBarcode 1 - Initial Data:\n" + barcodeToUpdate);
			barcodeToUpdate.setCode(barcode.getCode());
			barcodeToUpdate.setStartValidity(barcode.getStartValidity());
			barcodeToUpdate.setEndValidity(barcode.getEndValidity());
			barcodeToUpdate.setIsValid(barcode.getIsValid());
			
			logger.info("BarcodeServiceImpl GOOD updateBarcode 2 - Updated Data:\n" + barcodeToUpdate);
			return barcodeToUpdate;
		}
	}

	@Override
	public Boolean deleteBarcodeById(Integer id) {
		logger.info("BarcodeServiceImpl deleteBarcodeById Method");
		logger.info("BarcodeServiceImpl deleteBarcodeById - Given ID: " + id);
		Barcode res = repo.findById(id).orElse(null);
		if(res == null) {
			logger.info("BarcodeServiceImpl BAD deleteBarcodeById - Barcode not found: " + res);
			return false;
		}else {
			logger.info("BarcodeServiceImpl deleteBarcodeById 1 - Barcode found:\n" + res);
			try{
				repo.delete(res);
				logger.info("BarcodeServiceImpl GOOD deleteBarcodeById 2 - Barcode Deleted");
				return true;
			}catch(Exception e ) {
				e.printStackTrace();
				logger.info("BarcodeServiceImpl BAD deleteBarcodeById 2 - Barcode Not Deleted: " + e.getMessage());
				return false;
			}
			
		}
	}

}
