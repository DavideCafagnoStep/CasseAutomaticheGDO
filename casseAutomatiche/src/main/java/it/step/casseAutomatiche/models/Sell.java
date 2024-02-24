package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the sells database table.
 * 
 */
@Entity
@Table(name="sells")
@NamedQuery(name="Sell.findAll", query="SELECT s FROM Sell s")
public class Sell implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sell_id")
	private Integer id;

	@Column(name="sell_date")
	private Date date;

	@Column(name="sell_description")
	private String description;

	@Column(name="sell_price")
	private float price;

	@Column(name="sell_quantity")
	private Integer quantity;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="sell_department_id")
	private Department department;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="sell_pro_id")
	private Product product;

	//bi-directional many-to-one association to Receipt
	@ManyToOne
	@JoinColumn(name="sell_receipt_id")
	private Receipt receipt;

	public Sell() {
	}

	public Sell(Integer id, Date date, String description, float price, Integer quantity, Department department, Product product,
			Receipt receipt) {
		super();
		this.id = id;
		this.date = date;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.department = department;
		this.product = product;
		this.receipt = receipt;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Receipt getReceipt() {
		return this.receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	@Override
	public String toString() {
		return "Sell [id=" + id + ", date=" + date + ", description=" + description + ", price=" + price + ", quantity="
				+ quantity + ", department=" + department + ", product=" + product + ", receipt=" + receipt + "]";
	}
	
	

}