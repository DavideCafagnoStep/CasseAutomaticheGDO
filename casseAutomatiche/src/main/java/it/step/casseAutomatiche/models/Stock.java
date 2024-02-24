package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the stocks database table.
 * 
 */
@Entity
@Table(name="stocks")
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="stock_id")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="stock_date")
	private Date date;

	@Column(name="stock_num")
	private Integer num;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="stock_pro_id")
	private Product product;

	public Stock() {
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

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", date=" + date + ", num=" + num + ", product=" + product + "]";
	}
	
	

}