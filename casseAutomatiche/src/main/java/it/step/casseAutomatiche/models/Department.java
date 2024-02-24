package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the departments database table.
 * 
 */
@Entity
@Table(name="departments")
@NamedQuery(name="Department.findAll", query="SELECT d FROM Department d")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="department_id")
	private Integer id;

	@Column(name="department_description")
	private String description;

	//bi-directional many-to-one association to Product
	/*@OneToMany(mappedBy="department")
	private List<Product> products;

	//bi-directional many-to-one association to Sell
	@OneToMany(mappedBy="department")
	private List<Sell> sells;
*/
	public Department() {
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
/*
	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setDepartment(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setDepartment(null);

		return product;
	}

	public List<Sell> getSells() {
		return this.sells;
	}

	public void setSells(List<Sell> sells) {
		this.sells = sells;
	}

	public Sell addSell(Sell sell) {
		getSells().add(sell);
		sell.setDepartment(this);

		return sell;
	}

	public Sell removeSell(Sell sell) {
		getSells().remove(sell);
		sell.setDepartment(null);

		return sell;
	}
*/

	@Override
	public String toString() {
		return "Department [id=" + id + ", description=" + description + "]";
	}
	
	
}