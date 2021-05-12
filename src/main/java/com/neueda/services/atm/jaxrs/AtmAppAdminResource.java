package com.neueda.services.atm.jaxrs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neueda.services.atm.dao.ATMamount;
/**
 * AtmAppAdminResource provides RESTful service operations with Admin role access
 * 
 * @author kamalakarburugu
 *
 */


@RequestMapping("/account")
public interface AtmAppAdminResource {
	/**
	 * AtmAppAdminResource provides RESTful service operations to render notes available in Atm Machine with location of the ATM
	 * @param location
	 * @return ATMamount
	 */
	@GetMapping("/admin/atmnotes")
    public ResponseEntity<ATMamount> availablenotes(@RequestParam String location);

}
