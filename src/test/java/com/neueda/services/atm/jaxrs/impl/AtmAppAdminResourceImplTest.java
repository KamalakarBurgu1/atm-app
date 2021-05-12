package com.neueda.services.atm.jaxrs.impl;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.service.ATMService;
import com.project.bank.atm.main.ContextCreation;

@WebMvcTest(AtmAppAdminResourceImpl.class)
@AutoConfigureJsonTesters
class AtmAppAdminResourceImplTest extends ContextCreation {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ATMService atmService;

	@Autowired
	private JacksonTester<ATMamount> jsonTester;

	private ATMamount atmAmount = new ATMamount();

	@BeforeEach
	public void setup() throws Exception {
		atmAmount.setFifty(10);
		atmAmount.setTwenty(20);
		atmAmount.setTen(20);
		atmAmount.setFive(10);
		Mockito.when(atmService.findByLocation(ArgumentMatchers.anyString())).thenReturn(atmAmount);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void availablenotes_happyPath() throws Exception {
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.get("/account/admin/atmnotes").param("location", "ifsc")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		Assertions.assertThat(response.getStatus()).isEqualTo(200);
		Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(atmAmount).getJson());
	}

}