package ar.com.mercadolibre.services;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.mercadolibre.dtos.VerifiedDnaStats;
import ar.com.mercadolibre.entities.VerifiedDna;
import ar.com.mercadolibre.repositories.VerifiedDnaRepository;

@Service
public class VerifiedDnaService {
	
	private static final Logger logger = LoggerFactory.getLogger(VerifiedDnaService.class);
	
	@Autowired
	private VerifiedDnaRepository verifiedDnaRepository;
	
	public List<VerifiedDna> getVerifiedDnas() {
		return (List<VerifiedDna>) verifiedDnaRepository.findAll();
	}
	
	public VerifiedDnaStats getVerifiedDnaStats() {
		Map<String, BigInteger> result = verifiedDnaRepository.getVerifiedDnaStats();
		
		// Custom mapping
		VerifiedDnaStats verifiedDnaStats = new VerifiedDnaStats(
				result.get("MUTANTS"), 
				result.get("TOTAL"));
		
		return verifiedDnaStats;
	}

	public void saveRequestStat(String[] dna, Boolean isMutant) {
		
		String dnaString = Arrays.toString(dna);
		VerifiedDna verifiedDna = new VerifiedDna();
		verifiedDna.setDna(dnaString);
		verifiedDna.setIsMutant(isMutant);
		
		try {
			verifiedDnaRepository.save(verifiedDna);
		} catch(Exception e) {
			// Ignore duplicate keys
			logger.error(e.getMessage(), e);
		}
	}
}
