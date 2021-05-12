package com.neueda.services.atm.jaxrs;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * AtmAppResource provides RESTful service operations to render the particular resource Account balance details and 
 * supports the with draw operation
 
 * @author kamalakarburugu
 *
 */

@RequestMapping("/account")
public interface AtmAppUserResource {
	/**
	 * REST Web service to return the Account balance details
	 * 
	 * @return ResponseEntity<String>
	 */
	@GetMapping("/balance")
	public ResponseEntity<String> balancecheck();
	
	/**
	 * REST Web service to render the withdraw operation for specified amount
	 * with withdraw method, withdrawAmount and location
	 * @param withdrawAmount
	 * @param location
	 * @return ResponseEntity<String>
	 */
	@PostMapping("/withdraw")
	public ResponseEntity<String> withdraw(@RequestParam int withdrawAmount, @RequestParam String location)
			throws JsonProcessingException;

}
