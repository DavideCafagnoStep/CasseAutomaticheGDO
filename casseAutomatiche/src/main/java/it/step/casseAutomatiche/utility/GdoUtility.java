package it.step.casseAutomatiche.utility;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.step.casseAutomatiche.models.Sell;
import it.step.casseAutomatiche.models.Stock;

public class GdoUtility {
	
	public static List<Sell> sumSellDuplicate(List<Sell> lst){
		Map<Integer,Sell> res = new HashMap<Integer, Sell>();
		
		for(Sell s : lst) {
			Integer key = s.getProduct().getId().intValue();
			if(res.containsKey(key) == false) {
			 res.put(key, s);
			}else {
				Sell temp = res.get(key);
				Sell sellToUpdate = new Sell(temp.getId(), temp.getDate(),temp.getDescription(),temp.getPrice(),temp.getQuantity(), temp.getDepartment(), temp.getProduct(), temp.getReceipt());
				sellToUpdate.setQuantity(sellToUpdate.getQuantity().intValue() + s.getQuantity().intValue());
				System.out.println("\n\n"+sellToUpdate+"\n\n");
				res.put(key, sellToUpdate);
			}
		}
		
		List<Sell> response = new ArrayList<Sell>();
		
		for(Sell s : res.values()) {
			s.setPrice(s.getQuantity() * s.getPrice());
			response.add(s);
		}
		return response;
		
	}
	
	@SuppressWarnings("deprecation")
	public static Boolean isToday(Date date) {
		Date today = Date.valueOf(LocalDate.now());
		return date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDay() == today.getDay();
	}
	
	public static List<Stock> updateStock(List<Stock> lstStock, List<Sell> lstSell) {
		
		for(Sell sell:lstSell){
			Integer id = sell.getProduct().getId().intValue();
			Integer qty = sell.getQuantity().intValue();
			
			for(Stock stock :lstStock) {
				if(id == stock.getProduct().getId().intValue()) {
					stock.setNum(stock.getNum().intValue() - qty.intValue());
					stock.setDate(Date.valueOf(LocalDate.now()));				
					break;
				}
			}
		}
		return lstStock;
	}

}
