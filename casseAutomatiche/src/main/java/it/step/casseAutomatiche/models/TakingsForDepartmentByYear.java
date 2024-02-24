package it.step.casseAutomatiche.models;

import java.io.Serializable;

public class TakingsForDepartmentByYear implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String description;
	private Float total;
	private Integer year;
	public TakingsForDepartmentByYear() {}
	
	public TakingsForDepartmentByYear(String id, String description, Float total, Integer year) {
		super();
		this.id = id;
		this.description = description;
		this.total = total;
		this.year = year;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Takings For Department [ID:" + id + ", Department:" + description + ", TOTAL: " + total + ", Year: " + year
				+ "]\n";
	}
	
	
	

}
