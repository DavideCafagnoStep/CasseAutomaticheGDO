package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the prices database table.
 * 
 */
@Entity
@Table(name="prices")
@NamedQuery(name="Price.findAll", query="SELECT p FROM Price p")
public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="price_id")
	private Integer id;

	@Column(name="price_value")
	private float value;

	//bi-directional one-to-one association to Product
	@OneToOne
	@JoinColumn(name="price_product_id")
	private Product product;

	public Price() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
	}

public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", value=" + value + ", product=" + product + "]";
	}
	
	

}