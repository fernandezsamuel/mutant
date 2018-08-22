package ar.com.mercadolibre.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.mercadolibre.dtos.DnaRequest;
import ar.com.mercadolibre.dtos.VerifiedDnaStats;
import ar.com.mercadolibre.services.MutantService;
import ar.com.mercadolibre.services.VerifiedDnaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@RestController
@SwaggerDefinition(info = @Info(
        description = "Add Mutants info and allow to request statictics",
        version = "V0.1.0",
        title = "The Mutants API"
	))
public class MutantController {

	private static final Logger logger = LoggerFactory.getLogger(MutantController.class);
	
	@Autowired
	private MutantService mutantService;

	@Autowired
	private VerifiedDnaService verifiedDnaService;
    
	/**
	 * Returns this structure: ADN: {“count_mutant_dna”:[LONG], “count_human_dna”:[LONG], “ratio”:[DOUBLE]}
	 * @return 
	 */
	@ApiOperation(value = "Show mutants/humans statictics")
	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public Object stats() {
	  
		logger.debug("/stats");
		
		VerifiedDnaStats verifiedDnaStats = verifiedDnaService.getVerifiedDnaStats();
		return new ResponseEntity<>(verifiedDnaStats.toJsonObj() , HttpStatus.OK);
	}
	
	/**
	 * POST → /mutant/
		{
		“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
		}
	 * @param dnaContainer
	 * @return
	 */
	@ApiOperation(value = "Determines if given dna is mutant or not, and stores result")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Detected mutant dna"),
		      @ApiResponse(code = 403, message = "Detected non mutant dna (posible human)") })
    @RequestMapping(value = "/mutant", method = RequestMethod.POST, consumes="application/json")
    public Object mutant(@ApiParam(value = "Array of strings representing dna. Should generate a NxN char matrix", required = true)  @RequestBody DnaRequest dnaContainer) {
    	
    	Boolean isMutant = mutantService.isMutant(dnaContainer.getDna());
    	
    	logger.debug("/mutant: Arrives " + dnaContainer.getDna().toString() + ", result: isMutant=" + isMutant);
    	
    	verifiedDnaService.saveRequestStat(dnaContainer.getDna(), isMutant);
    	
    	// En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un 403-Forbidden
    	return new ResponseEntity<>(isMutant?HttpStatus.OK: HttpStatus.FORBIDDEN);
    }
}
