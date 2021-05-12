package com.neueda.services.atm.service;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.dao.User;
import com.neueda.services.atm.repository.UserRepository;
import com.neueda.services.atm.util.Withdraw;
import com.project.bank.atm.main.ContextCreation;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserService.class)
class UserServiceTest  extends ContextCreation{
	
	@Autowired
	private UserService userService;
	
	
	private Map<Integer,Integer> dispenseAmount;
	private User mockUser;
	
	@MockBean
	public Withdraw withdraw;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ATMService atmService;
	
	@MockBean
	private SecurityContext context;
	
	@MockBean
	private Authentication authentication;
	
	
	@BeforeEach
	public void setup() {
		
		
		mockUser = new User();
		mockUser.setAccountnumber("123");
		
		dispenseAmount = new HashMap<>();
		dispenseAmount.put(50, 10);
		dispenseAmount.put(20, 5);
		dispenseAmount.put(10, 0);
		dispenseAmount.put(5, 0);
		
		
	    Mockito.when(authentication.getPrincipal()).thenReturn(mockUser);
	    Mockito.when(context.getAuthentication()).thenReturn(authentication);
	    SecurityContextHolder.setContext(context);
		
		Mockito.when(withdraw.withdraw(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyInt(), ArgumentMatchers.any())).thenReturn(dispenseAmount);
		Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(new User());
		Mockito.when(userRepository.findByAccountnumber(ArgumentMatchers.any())).thenReturn(mockUser);
		Mockito.when(atmService.findByLocation(ArgumentMatchers.anyString())).thenReturn(new ATMamount());

	}

    @Test
    void findByAccountnumber_happyPath() {
    	User response = userService.findByAccountnumber();
    	Assertions.assertThat(mockUser.getAccountnumber()).isEqualTo(response.getAccountnumber());
    
    }

    @Test
    void withdraw_with_proper_parameter_passed_returns_success() {
    	Map<Integer,Integer> response = userService.withdraw(600, "ifsc", new StringBuilder(" "));
    	Assertions.assertThat(dispenseAmount.toString()).isEqualTo(response.toString());
    }
}