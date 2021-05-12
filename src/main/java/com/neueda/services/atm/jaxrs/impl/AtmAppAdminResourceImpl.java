package com.neueda.services.atm.jaxrs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.jaxrs.AtmAppAdminResource;
import com.neueda.services.atm.service.ATMService;
/**
 * AtmAppAdminResource provides RESTful service operations with Admin role access
 * 
 * @author kamalakarburugu
 *
 */

@RestController
public class AtmAppAdminResourceImpl implements AtmAppAdminResource {

    @Autowired
    private ATMService atmService;
   
    /**
	 * AtmAppAdminResource provides RESTful service operations to render notes available in Atm Machine with location of the ATM
	 * @param location
	 * @return ATMamount
	 */
   @Override
@GetMapping("/admin/atmnotes")
    public ResponseEntity<ATMamount> availablenotes(@RequestParam String location){

        var atMamount = atmService.findByLocation(location);
        return ResponseEntity.ok(atMamount);
    }
}
