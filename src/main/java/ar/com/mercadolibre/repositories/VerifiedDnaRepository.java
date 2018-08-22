package ar.com.mercadolibre.repositories;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.mercadolibre.entities.VerifiedDna;

@Repository
public interface VerifiedDnaRepository extends CrudRepository<VerifiedDna, Long>{

	
	@Query(value ="SELECT "
			+ "SUM(CASE WHEN d.is_mutant THEN 1 ELSE 0 END) as mutants, "
			+ "COUNT(1) as total "
			+ "FROM verified_dnas d", 
			nativeQuery=true)
    Map<String, BigInteger> getVerifiedDnaStats();
}