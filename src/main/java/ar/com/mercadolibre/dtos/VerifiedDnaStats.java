package ar.com.mercadolibre.dtos;

import java.math.BigInteger;

import org.json.simple.JSONObject;

public class VerifiedDnaStats {

	private BigInteger mutants;
	private BigInteger total;
	
	public VerifiedDnaStats() {
		super();
	}
	
	public VerifiedDnaStats(BigInteger mutants, BigInteger total) {
		super();
		this.mutants = (mutants == null? BigInteger.ZERO: mutants);
		this.total = (total == null? BigInteger.ZERO: total);
	}
	public BigInteger getMutants() {
		return mutants;
	}
	public void setMutants(BigInteger mutants) {
		this.mutants = mutants;
	}
	public BigInteger getTotal() {
		return total;
	}
	public void setTotal(BigInteger total) {
		this.total = total;
	}
	public BigInteger getHumans() {
		return total.subtract(mutants);
	}
	public Double getRatio() {
		if (getHumans().equals(BigInteger.ZERO)) {return (double)0;} // prevent zero division
		
		Double ratio = mutants.doubleValue() / getHumans().doubleValue();
		ratio = (double)((int)(ratio * 100)) / 100; // 2 decimals
		return ratio;
	}

	/**
	 * Returns this structure: ADN: {“count_mutant_dna”:40, “count_human_dna”:100, “ratio”:0.4}
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJsonObj() {
		JSONObject response = new JSONObject();
		
		JSONObject dna = new JSONObject();
		response.put("ADN", dna);
		
		dna.put("count_mutant_dna", this.getMutants());
		dna.put("count_human_dna", this.getHumans());
		dna.put("ratio", this.getRatio());
		
		return response;
	}
}
