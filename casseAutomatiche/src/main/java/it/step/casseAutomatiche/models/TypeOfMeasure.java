package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the type_of_measures database table.
 * 
 */
@Entity
@Table(name="type_of_measures")
@NamedQuery(name="TypeOfMeasure.findAll", query="SELECT t FROM TypeOfMeasure t")
public class TypeOfMeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tom_id")
	private Integer id;

	@Column(name="tom_description")
	private String description;

	//bi-directional many-to-one association to Product
	/*@OneToMany(mappedBy="typeOfMeasure")
	private List<Product> products;
*/
	public TypeOfMeasure() {
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


	/*public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setTypeOfMeasure(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setTypeOfMeasure(null);

		return product;
	}
*/
	
	@Override
	public String toString() {
		return "TypeOfMeasure [id=" + id + ", description=" + description + "]";
	}
	
}