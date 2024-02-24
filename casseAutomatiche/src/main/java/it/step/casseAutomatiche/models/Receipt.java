package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the receipts database table.
 * 
 */
@Entity
@Table(name="receipts")
@NamedQuery(name="Receipt.findAll", query="SELECT r FROM Receipt r")
public class Receipt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="receipt_id")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="receipt_date")
	private Date date;

	@Column(name="receipt_total")
	private float total;

	//bi-directional many-to-one association to Sell
/*	@OneToMany(mappedBy="receipt")
	private List<Sell> sells;
*/
	public Receipt() {
	}

	public Receipt(Integer id, Date date, float total) {
		super();
		this.id = id;
		this.date = date;
		this.total = total;
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

	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
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
		sell.setReceipt(this);

		return sell;
	}

	public Sell removeSell(Sell sell) {
		getSells().remove(sell);
		sell.setReceipt(null);

		return sell;
	}
*/

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", date=" + date + ", total=" + total + "]";
	}
	
	
}