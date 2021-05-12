package com.neueda.services.atm.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neueda.services.atm.repository.UserRepository;
import com.neueda.services.atm.securityutil.CustomUser;

@Service
public class CustomUserDetailService implements UserDetailsService {

	private static final Log logger = LogFactory.getLog(CustomUserDetailService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String accountnumber) {
		var user = userRepository.findByAccountnumber(accountnumber);
		if (user == null)
			throw new UsernameNotFoundException("AccountNumber: " + accountnumber + " " + "not found");
		return new CustomUser(user);
	}
}
