package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.Stock;

public interface StockService {
	
	public List<Stock> getAll();
	public Stock getOneById(Integer id);
	public Stock saveStock(Stock stock);
	public Stock updateStock(Stock stock);
	public Boolean deleteStockById(Integer id);

}
