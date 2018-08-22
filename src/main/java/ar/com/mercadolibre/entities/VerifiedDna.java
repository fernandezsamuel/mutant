package ar.com.mercadolibre.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "verified_dnas")
public class VerifiedDna {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique=true)
	private String dna;
	@Column
	private Boolean isMutant;
	
	public VerifiedDna() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDna() {
		return dna;
	}
	public void setDna(String dna) {
		this.dna = dna;
	}
	public Boolean getIsMutant() {
		return isMutant;
	}
	public void setIsMutant(Boolean isMutant) {
		this.isMutant = isMutant;
	}
}
