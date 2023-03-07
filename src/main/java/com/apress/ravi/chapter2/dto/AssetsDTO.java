package com.apress.ravi.chapter2.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class AssetsDTO {

	@Id
	@GeneratedValue
	@Column(name = "ASSET_ID")
	private Long id;

	@NotEmpty(message = "error.name.empty")
	@Length(max = 50, message = "error.name.length")
	@Column(name = "NAME")
	private String name;

	@NotEmpty(message = "error.manufacturer.empty")
	@Length(max = 150, message = "error.manufacturer.length")
	@Column(name = "MANUFACTURER")
	private String manufacturer;

	@NotEmpty(message = "error.price.empty")
	@Column(name = "PRICE")
	private Float price;

	public Long getId() {
		return id;
	}

	public String getIdStr() {
		return String.valueOf(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Float getPrice() {
		return price;
	}

	public String getPriceStr() {
		return String.valueOf(price);
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
