package it.step.casseAutomatiche.models;

import java.io.Serializable;
import java.sql.Date;

public class TakingsForProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String description;
	private Float total;
	private Date date;
	public TakingsForProduct() {}
	
	public TakingsForProduct(String id, String description, Float total, Date date) {
		super();
		this.id = id;
		this.description = description;
		this.total = total;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Takings For Product [ID:" + id + ", PRODUCT:" + description + ", TOTAL: " + total + ", DATE: " + date
				+ "]\n";
	}
	
	
	

}
