package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name="products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pro_id")
	private Integer id;

	@Column(name="pro_description")
	private String description;

	@Column(name="pro_weight")
	private Integer weight;

	//bi-directional many-to-one association to Barcode
/*	@OneToMany(mappedBy="product")
	private List<Barcode> barcodes;
*/
	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="pro_department_id")
	private Department department;

	//bi-directional many-to-one association to TypeOfMeasure
	@ManyToOne
	@JoinColumn(name="pro_type_of_measure_id")
	private TypeOfMeasure typeOfMeasure;

	//bi-directional many-to-one association to Sell
/*	@OneToMany(mappedBy="product")
	private List<Sell> sells;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="product")
	private List<Stock> stocks;

	//bi-directional one-to-one association to Price
	@OneToOne(mappedBy="product")
	private Price price;
*/
	public Product() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weigth) {
		this.weight = weigth;
	}
/*
	public List<Barcode> getBarcodes() {
		return this.barcodes;
	}

	public void setBarcodes(List<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	public Barcode addBarcode(Barcode barcode) {
		getBarcodes().add(barcode);
		barcode.setProduct(this);

		return barcode;
	}

	public Barcode removeBarcode(Barcode barcode) {
		getBarcodes().remove(barcode);
		barcode.setProduct(null);

		return barcode;
	}
*/
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public TypeOfMeasure getTypeOfMeasure() {
		return this.typeOfMeasure;
	}

	public void setTypeOfMeasure(TypeOfMeasure typeOfMeasure) {
		this.typeOfMeasure = typeOfMeasure;
	}
/*
	public List<Sell> getSells() {
		return this.sells;
	}

	public void setSells(List<Sell> sells) {
		this.sells = sells;
	}

	public Sell addSell(Sell sell) {
		getSells().add(sell);
		sell.setProduct(this);

		return sell;
	}

	public Sell removeSell(Sell sell) {
		getSells().remove(sell);
		sell.setProduct(null);

		return sell;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setProduct(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setProduct(null);

		return stock;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
*/

	@Override
	public String toString() {
		return "Product [id=" + id + ", description=" + description + ", weigth=" + weight + ", department="
				+ department + ", typeOfMeasure=" + typeOfMeasure + "]";
	}
	
	
}