package com.neueda.services.atm.service;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.repository.ATMRepository;


@Service
public class ATMService {

	private static final Log logger = LogFactory.getLog(ATMService.class);

	@Autowired
	private ATMRepository atmRepository;

	public ATMamount findByLocation(String location) {
		
		return atmRepository.findByLocation(location);
	}

	@Transactional
	public void save(ATMamount atMamount) {
		atmRepository.save(atMamount);
	}
}
