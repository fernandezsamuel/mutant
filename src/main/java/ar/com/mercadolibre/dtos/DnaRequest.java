package ar.com.mercadolibre.dtos;

public class DnaRequest {

	private String[] dna;

	public DnaRequest() {
		super();
	}
	public DnaRequest(String[] dna) {
		super();
		this.dna = dna;
	}
	public String[] getDna() {
		return dna;
	}
	public void setDna(String[] dna) {
		this.dna = dna;
	}
}
