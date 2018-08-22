package ar.com.mercadolibre;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ar.com.mercadolibre.services.MutantService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class MutantServiceTests {
	
	@Autowired
	 private MutantService mutantService;

	 
	 @Test
	 public void isMutantTest_1() throws Exception {
		 String[] dna = {
				 "ATGCGA",
				 "CAGTGC",
				 "TTATGT",
				 "AGAAGG",
				 "CCCCTA",
				 "TCACTG"};
		 
		 Assert.assertTrue("Debe ser mutante", this.mutantService.isMutant(dna));
	 }
	 
	 @Test
	 public void isNotMutant() throws Exception {
		 String[] dna = {
				 "ATGCGA",
				 "CAGTGC",
				 "TTGTGT",
				 "AGAAGG",
				 "CTCCTA",
				 "TCACTG"};
		 
		 Assert.assertFalse("No debe ser mutante", this.mutantService.isMutant(dna));
	 }
	 
	 @Test
	 public void mutantSearchWithStress() throws Exception {
		 
		 String[] dna = {
				 "ATGCGA",
				 "CAGTGC",
				 "TTATGT",
				 "AGAAGG",
				 "CCCCTA",
				 "TCACTG"};
			
		 for(int i=0; i< 1000000; i++) {
			 this.mutantService.isMutant(dna);
		 }
		 
	 }
	 
	 @Test
	 public void dnaMatcher() {
		 
		 final String pattern = "[ATCG]+";
		 String dna = "ATGCGA";
		 Assert.assertTrue(dna.matches(pattern));

		 dna = "";
		 Assert.assertFalse(dna.matches(pattern));
		 
		 dna = "ATGWCGA";
		 Assert.assertFalse(dna.matches(pattern));
	 }
	
}