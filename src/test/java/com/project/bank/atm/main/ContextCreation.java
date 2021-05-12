package com.project.bank.atm.main;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.neueda.services.atm.service.CustomUserDetailService;
import com.neueda.services.atm.util.CustomAuthenticationEntryPoint;


public class ContextCreation {
	
	
	@MockBean
	public  CustomUserDetailService custom;
	
	@MockBean
	public CustomAuthenticationEntryPoint entrypoint;
	

}
