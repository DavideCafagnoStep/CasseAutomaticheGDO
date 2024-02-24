package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.Price;
import it.step.casseAutomatiche.models.Product;

public interface PriceService {
			
	public List<Price> getAll();
	public Price getOneById(Integer id);
	public Price savePrice(Price price);
	public Price updatePrice(Price price);
	public Boolean deletePriceById(Integer id);
	public Price getOneByProduct(Product product);

}
