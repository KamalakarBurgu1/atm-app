package com.neueda.services.atm.jaxrs.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.neueda.services.atm.dao.Account;
import com.neueda.services.atm.dao.User;
import com.neueda.services.atm.dao.UserRole;
import com.neueda.services.atm.service.UserService;
import com.project.bank.atm.main.ContextCreation;

@WebMvcTest(AtmAppUserRespourceImpl.class)
@WithMockUser(roles="USER")
@AutoConfigureJsonTesters
public class AtmAppUserRespourceImplTest extends ContextCreation{

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	public UserService userService;
	
	@Autowired
	private JacksonTester<Map<Integer,Integer>> json;
	
	private Map<Integer,Integer> mockDispenseAmount;
	
	@BeforeEach
	public void setup() {
		Account mockAccount = new Account();
		mockAccount.setBalance(300);
		mockAccount.setOverdraft(200);
		
		Set<UserRole> mockRoles = new HashSet<>();
		mockRoles.add(UserRole.ROLE_USER);
		
		User mockUser = new User();
		mockUser.setAccountnumber("123");
		mockUser.setPin(new BCryptPasswordEncoder().encode("123"));
		mockUser.setRoles(mockRoles);
		mockUser.setAccount(mockAccount);
		
		mockDispenseAmount = new HashMap<>();
		mockDispenseAmount.put(50, 6);
		mockDispenseAmount.put(20, 0);
		mockDispenseAmount.put(10, 0);
		mockDispenseAmount.put(5, 0);
		
		Mockito.when(userService.findByAccountnumber()).thenReturn(mockUser);
		Mockito.when(userService.withdraw(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(mockDispenseAmount);
		
	}
	 
	
	@Test
    public void balancecheck_happyPath() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/account/balance").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().string("Available balance is::300 Maximum Withdraw Possible::500"));
	}

    @Test
    public void withdraw_with_proper_parameter_passed_returns_success() throws Exception {
    	
    	MockHttpServletResponse response =  mockMvc.perform(MockMvcRequestBuilders.post("/account/withdraw")
    			.accept(MediaType.APPLICATION_JSON)
    			.contentType(MediaType.APPLICATION_JSON)
    			.param("withdrawAmount", "300")
    			.param("location", "ifsc"))
    			.andReturn().getResponse();
    	Assertions.assertThat(response.getContentAsString()).isEqualTo(json.write(mockDispenseAmount).getJson()+" -- ");
    			
    }
  
}