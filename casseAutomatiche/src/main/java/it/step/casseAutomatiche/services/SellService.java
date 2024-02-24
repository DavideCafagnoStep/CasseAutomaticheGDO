package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.Sell;

public interface SellService {

	public List<Sell> getAll();
	public Sell getOneById(Integer id);
	public Sell saveSell(Sell sell);
	public List<Sell> saveAllSell(List<Sell> sells);
	public Sell updateSell(Sell sell);
	public Boolean deleteSellById(Integer id);
	

}
