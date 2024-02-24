package it.step.casseAutomatiche.models;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the barcodes database table.
 * 
 */
@Entity
@Table(name="barcodes")
@NamedQuery(name="Barcode.findAll", query="SELECT b FROM Barcode b")
public class Barcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="barcode_id")
	private Integer id;

	@Column(name="barcode_code")
	private String code;

	@Column(name="barcode_end_validity")
	private Date endValidity;

	
	@Column(name="barcode_start_validity")
	private Date startValidity;

	@Column(name="barcode_valid")
	private Boolean isValid;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="barcode_pro_id")
	private Product product;

	public Barcode() {
	}
	

	public Barcode(Integer id, String code, Date endValidity, Date startValidity, Boolean isValid, Product product) {
		super();
		this.id = id;
		this.code = code;
		this.endValidity = endValidity;
		this.startValidity = startValidity;
		this.isValid = isValid;
		this.product = product;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getEndValidity() {
		return this.endValidity;
	}

	public void setEndValidity(Date endValidity) {
		this.endValidity = endValidity;
	}

	public Date getStartValidity() {
		return this.startValidity;
	}

	public void setStartValidity(Date startValidity) {
		this.startValidity = startValidity;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Barcode [id=" + id + ", code=" + code + ", endValidity=" + endValidity + ", startValidity="
				+ startValidity + ", isValid=" + isValid + ", product=" + product + "]";
	}
	
	

}