package com.neueda.services.atm.service;

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

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.repository.ATMRepository;
import com.project.bank.atm.main.ContextCreation;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ATMService.class)
class ATMServiceTest extends ContextCreation{
	
	@MockBean
	private ATMRepository atmRepository;
	
	@Autowired
	private ATMService atmService;
	
	private ATMamount mockAtmAmount;
	
	@BeforeEach
	public void setup() {
		mockAtmAmount = new ATMamount();
		mockAtmAmount.setFifty(10);
		Mockito.when(atmRepository.findByLocation(ArgumentMatchers.anyString())).thenReturn(mockAtmAmount);
	}

    @Test
    void findByLocation_happyPath() {
    	ATMamount response = atmService.findByLocation("ifsc");
    	Assertions.assertThat(mockAtmAmount.getFifty()).isEqualTo(response.getFifty());
    }
   
}