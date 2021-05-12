package com.neueda.services.atm.jaxrs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.services.atm.dao.User;
import com.neueda.services.atm.jaxrs.AtmAppUserResource;
import com.neueda.services.atm.service.UserService;

/**
 * AtmAppResource provides RESTful service operations to render the particular resource Account balance details and 
 * supports the with draw operation
 
 * @author kamalakarburugu
 *
 */

@RequestMapping("/account")
@RestController
public class AtmAppUserRespourceImpl implements AtmAppUserResource {

	@Autowired
	private UserService userService;

	/**
	 * REST Web service to return the Account balance details
	 * 
	 * @return ResponseEntity<String>
	 */
	@Override
	@GetMapping("/balance")
	public ResponseEntity<String> balancecheck() {
		User user = userService.findByAccountnumber();
		int balance = user.getAccount().getBalance();
		int totalBalance = user.getAccount().getOverdraft() + balance;
		String responseString = "Available balance is::" + balance + " Maximum Withdraw Possible::" + totalBalance;
		return ResponseEntity.ok(responseString);
	}

	/**
	 * REST Web service to render the withdraw operation for specified amount
	 * with withdraw method, withdrawAmount and location
	 * @param withdrawAmount
	 * @param location
	 * @return ResponseEntity<String>
	 */
	@Override
	@PostMapping("/withdraw")
	public ResponseEntity<String> withdraw(@RequestParam int withdrawAmount, @RequestParam String location)
			throws JsonProcessingException {
		StringBuilder message = new StringBuilder();
		var dispenseAmount = userService.withdraw(withdrawAmount, location, message);
		if ((dispenseAmount.get(50) > 0) || (dispenseAmount.get(20) > 0) || (dispenseAmount.get(10) > 0)
				|| (dispenseAmount.get(5) > 0))
			return ResponseEntity.ok()
					.body((new ObjectMapper().writeValueAsString(dispenseAmount) + " -- " + message.toString()));
		return ResponseEntity.badRequest().body(message.toString());
	}
}
