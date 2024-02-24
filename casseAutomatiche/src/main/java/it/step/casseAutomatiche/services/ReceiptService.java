package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.Receipt;

public interface ReceiptService {
	
	public List<Receipt> getAll();
	public Receipt getOneById(Integer id);
	public Receipt saveReceipt(Receipt receipt);
	public Receipt updateReceipt(Receipt receipt);
	public Boolean deleteReceiptById(Integer id);

}
