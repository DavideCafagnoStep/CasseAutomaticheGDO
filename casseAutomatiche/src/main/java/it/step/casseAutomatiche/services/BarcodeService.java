package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.Barcode;

public interface BarcodeService {
		
	public List<Barcode> getAll();
	public Barcode getOneById(Integer id);
	public Barcode saveBarcode(Barcode barcode);
	public Barcode updateBarcode(Barcode barcode);
	public Boolean deleteBarcodeById(Integer id);

}
