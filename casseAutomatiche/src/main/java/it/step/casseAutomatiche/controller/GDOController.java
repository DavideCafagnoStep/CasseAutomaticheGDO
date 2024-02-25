package it.step.casseAutomatiche.controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.step.casseAutomatiche.JDBC.JDBC;
import it.step.casseAutomatiche.models.Barcode;
import it.step.casseAutomatiche.models.Price;
import it.step.casseAutomatiche.models.Product;
import it.step.casseAutomatiche.models.Receipt;
import it.step.casseAutomatiche.models.Sell;
import it.step.casseAutomatiche.models.Stock;
import it.step.casseAutomatiche.models.TakingsForDepartment;
import it.step.casseAutomatiche.models.TakingsForDepartmentByYear;
import it.step.casseAutomatiche.models.TakingsForProduct;
import it.step.casseAutomatiche.services.BarcodeService;
import it.step.casseAutomatiche.services.PriceService;
import it.step.casseAutomatiche.services.ReceiptService;
import it.step.casseAutomatiche.services.SellService;
import it.step.casseAutomatiche.services.StockService;
import it.step.casseAutomatiche.utility.GdoUtility;
import it.step.casseAutomatiche.utility.QueryConstants;

@RestController
@RequestMapping(value="/gdo")
public class GDOController {
	
	
		
	@Autowired
	private BarcodeService barcodeService;
	
	@Autowired
	private SellService sellService;
	
	@Autowired
	private ReceiptService receiptService;
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private StockService stockService;
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value="/buy",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Sell>> buyProductByBarcodes(@RequestBody List<Barcode> inputBarcodes){
		logger.info("GDOController buyProductByBarcodes Method");
		ResponseEntity<List<Sell>> res = null; 
		List<Sell> receiptSells = new ArrayList<Sell>();
		float tot = 0;
		
		if(inputBarcodes.size() == 0) {
			logger.info("GDOController buyProductByBarcodes NO PRODUCT TO BUY");
			res = ResponseEntity.notFound().build();
		}else {
			logger.info("GDOController buyProductByBarcodes PRODUCT BARCODES TO BUY:\n ", inputBarcodes);
			Receipt receipt =  receiptService.saveReceipt(new Receipt(0,Date.valueOf(LocalDate.now()), tot));
			if(receipt != null) {
				
				for(Barcode inputB : inputBarcodes) {
					Barcode barcodeDB = barcodeService.getOneById(inputB.getId());
					if(barcodeDB != null) {
						logger.info("GDOController buyProductByBarcodes BARCODE FOUND -> ", barcodeDB);
						if(barcodeDB.getStartValidity().before( Date.valueOf(LocalDate.now())) && barcodeDB.getEndValidity().after( Date.valueOf(LocalDate.now()))) {
							if(barcodeDB.getIsValid() == false) {
								barcodeService.updateBarcode(new Barcode(barcodeDB.getId(), barcodeDB.getCode(), barcodeDB.getEndValidity(), barcodeDB.getStartValidity(), true, barcodeDB.getProduct()));
							}
							Product product = barcodeDB.getProduct();
							Price price = priceService.getOneByProduct(product);
							if(price != null) {
								Sell sell = new Sell(null, Date.valueOf(LocalDate.now()), product.getDescription(), price.getValue(), 1, product.getDepartment(), product, receipt);
								logger.info("GDOController buyProductByBarcodes SELL Prepared -> "+ sell);
								receiptSells.add(sell);
							}else {
								logger.info("GDOController buyProductByBarcodes BARCODE PRICE NOT FOUND -> "+ price);
							}
							
						}else {
							logger.info("GDOController buyProductByBarcodes BARCODE INVALID -> "+ barcodeDB);
							if(barcodeDB.getIsValid()==true) {
								barcodeService.updateBarcode(new Barcode(barcodeDB.getId(), barcodeDB.getCode(), barcodeDB.getEndValidity(), barcodeDB.getStartValidity(), false, barcodeDB.getProduct()));
							}
						}
						
						
					}else {
						logger.info("GDOController buyProductByBarcodes BARCODE NOT FOUND -> ", inputB);
					}
				}
			
				try{
					logger.info("GDOController buyProductByBarcodes Final SELL View: \n"+ receiptSells);
					receiptSells = GdoUtility.sumSellDuplicate(receiptSells);
					
					List<Sell> responseSells = sellService.saveAllSell(receiptSells);
					if(responseSells.size() == 0) {
						logger.info("GDOController buyProductByBarcodes NOT FOUND: "+ responseSells);
						res = ResponseEntity.notFound().build();
						
						
					}else {
						for(Sell s : responseSells) {
							tot+= s.getPrice();
						}
						if(receipt.getTotal() != tot) {
							receipt.setTotal(tot);
							receiptService.updateReceipt(receipt);
						}
						logger.info("GDOController GOOD buyProductByBarcodes Result:\n"+ responseSells);
						res = ResponseEntity.ok(responseSells);
					}
					
				}catch(Exception e) {
					e.printStackTrace();
					logger.info("GDOController BAD buyProductByBarcodes Final SELL Error: ", e.getMessage());
					res = ResponseEntity.internalServerError().build();
				}
				
			}else {
				logger.info("GDOController buyProductByBarcodes NO RECEIPT AVIABLE");
				res = ResponseEntity.internalServerError().build();
			}
		}
		
		return res;
				
	}
	
	@GetMapping(value="/stock", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Stock>> calculateDailyStocks(){
		logger.info("GDOController calculateDailyStocks Method");
		ResponseEntity<List<Stock>> res = null;
		List<Sell> lstSellByDayGroupByProduct = new ArrayList<Sell>();
		List<Stock> lstStock = new ArrayList<Stock>();
		
		for(Sell s : sellService.getAll()) {
			if (GdoUtility.isToday(s.getDate())) {
				lstSellByDayGroupByProduct.add(new Sell(null, s.getDate(), s.getDescription(), s.getPrice(), s.getQuantity(), s.getDepartment(), s.getProduct(), s.getReceipt()));
			}
		}
		lstSellByDayGroupByProduct = GdoUtility.sumSellDuplicate(lstSellByDayGroupByProduct);
		logger.info("GDOController calculateDailyStocks lstSellByDayGroupByProduct:\n" + lstSellByDayGroupByProduct);
		
		lstStock = stockService.getAll();
		logger.info("GDOController calculateDailyStocks lstStock Before:\n" + lstStock);
		if(lstStock.size()>0) {
			lstStock = GdoUtility.updateStock(lstStock, lstSellByDayGroupByProduct);
			logger.info("GDOController calculateDailyStocks lstStock Updated:\n" + lstStock);
			lstStock.forEach(s->{
				stockService.updateStock(s);
			});
			res = ResponseEntity.ok(lstStock);
		}else {
			logger.info("GDOController calculateDailyStocks lstStock NOT FOUND: " + lstStock);
			res = ResponseEntity.notFound().build();
		}
		
		
		return res ;
		
	}
	
	@GetMapping(value="/daily-takings", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getDailyTakings(){
		logger.info("GDOController getDailyTakings Method");
		ResponseEntity<List<String>> res = null;
		List<String> takings = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs =  null;
		try {
			ps = JDBC.getConnection().prepareStatement(QueryConstants.INCASSO_GIORNATA);
			rs =  ps.executeQuery();
			while(rs.next()) {
				takings.add("TOTAL DAILY TAKINGS: " + rs.getFloat("total"));
			}
			
			logger.info("GDOController GOOD getDailyTakings - result: ", takings);
			res = ResponseEntity.ok(takings);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("GDOController BAD getDailyTakings - SQL ERROR", e.getMessage());
		}finally {
			JDBC.closePsAndRs(ps, rs);
		}
		
		
		return res;
	}
	
	@GetMapping(value="/takings4product/bydate/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TakingsForProduct>> getTakingsForProductsByDate(@PathVariable("date") Date date){
		logger.info("GDOController getTakingsForProductByDate Method");
		ResponseEntity<List<TakingsForProduct>> res = null;
		List<TakingsForProduct> takings = new ArrayList<TakingsForProduct>();
		PreparedStatement ps = null;
		ResultSet rs =  null;
		try {
			ps = JDBC.getConnection().prepareStatement(QueryConstants.INCASSO_DATA_UNA_GIORNATA_PER_ARTICOLO);
			ps.setDate(1, date);
			rs =  ps.executeQuery();
			while(rs.next()) {
				String id = rs.getInt("pro_id") + "";
				String product =rs.getString("product");
				Integer quantity = rs.getInt("quantity");
				Float total = rs.getFloat("total") ;
				TakingsForProduct taking = new TakingsForProduct(id, product, quantity, total, date);
				takings.add(taking);
			}
			
			logger.info("GDOController GOOD getTakingsForProductByDate - result: ", takings);
			res = ResponseEntity.ok(takings);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("GDOController BAD getTakingsForProductByDate - SQL ERROR", e.getMessage());
		}finally {
			JDBC.closePsAndRs(ps, rs);
		}
		
		
		return res;
	}
	
	@GetMapping(value="/takings4department/bydate/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TakingsForDepartment>> getTakingsForDepartmentsByDate(@PathVariable("date") Date date){
		logger.info("GDOController getTakingsForDepartmentsByDate Method");
		ResponseEntity<List<TakingsForDepartment>> res = null;
		List<TakingsForDepartment> takings = new ArrayList<TakingsForDepartment>();
		PreparedStatement ps = null;
		ResultSet rs =  null;
		try {
			ps = JDBC.getConnection().prepareStatement(QueryConstants.INCASSO_PER_REPARTO_DATA_UNA_GIORNATA);
			ps.setDate(1, date);
			rs =  ps.executeQuery();
			while(rs.next()) {
				String id = rs.getInt("department_id") + "";
				String department =rs.getString("department");
				Float total = rs.getFloat("total") ;
				TakingsForDepartment taking = new TakingsForDepartment(id, department, total, date);
				takings.add(taking);
			}
			
			logger.info("GDOController GOOD getTakingsForDepartmentsByDate - result: ", takings);
			res = ResponseEntity.ok(takings);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("GDOController BAD getTakingsForDepartmentsByDate - SQL ERROR", e.getMessage());
		}finally {
			JDBC.closePsAndRs(ps, rs);
		}
		
		
		return res;
	}
	
	@GetMapping(value="/takings4department/byyear/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TakingsForDepartmentByYear>> getTakingsForDepartmentsByYear(@PathVariable("year") Integer year){
		logger.info("GDOController getTakingsForDepartmentsByYear Method");
		ResponseEntity<List<TakingsForDepartmentByYear>> res = null;
		List<TakingsForDepartmentByYear> takings = new ArrayList<TakingsForDepartmentByYear>();
		PreparedStatement ps = null;
		ResultSet rs =  null;
		try {
			ps = JDBC.getConnection().prepareStatement(QueryConstants.INCASSO_PER_REPARTO_DATO_UN_ANNO);
			ps.setInt(1, year.intValue());
			rs =  ps.executeQuery();
			while(rs.next()) {
				String id = rs.getInt("department_id") + "";
				String department =rs.getString("department");
				Float total = rs.getFloat("total") ;
				TakingsForDepartmentByYear taking = new TakingsForDepartmentByYear(id, department, total, year.intValue());
				takings.add(taking);
			}
			
			logger.info("GDOController GOOD getTakingsForDepartmentsByYear - result: ", takings);
			res = ResponseEntity.ok(takings);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("GDOController BAD getTakingsForDepartmentsByYear - SQL ERROR", e.getMessage());
		}finally {
			JDBC.closePsAndRs(ps, rs);
		}
		
		
		return res;
	}

}
