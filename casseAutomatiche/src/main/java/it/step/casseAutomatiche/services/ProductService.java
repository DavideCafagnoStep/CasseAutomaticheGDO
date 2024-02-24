package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.Product;

public interface ProductService {
	
	public List<Product> getAll();
	public Product getOneById(Integer id);
	public Product saveProduct(Product product);
	public List<Product> saveAllProduct(List<Product> products);
	public Product updateProduct(Product product);
	public Boolean deleteProductById(Integer id);

}
