package com.apap.tutorial4.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dealer")
public class DealerModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 50)
	@Column(name = "alamat", nullable = false)
	private String alamat;

	@NotNull
	@Size(max = 13)
	@Column(name = "no_telp", nullable = false)
	private String noTelp;

	@OneToMany(mappedBy = "dealer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CarModel> listCar;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
	public void setNoTelp(String noTelp) {
		this.noTelp = noTelp;
	}
	
	public void setListCar(List<CarModel> listCar) {
		this.listCar = listCar;
	}
	
	public long getId() {
		return id;
	}
	
	public String getAlamat() {
		return alamat;
	}
	
	public String getNoTelp() {
		return noTelp;
	}


}
