package com.neueda.services.atm.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.dao.Account;
import com.neueda.services.atm.dao.User;
import com.neueda.services.atm.dao.UserRole;

class WithdrawTest {

	private Withdraw mockWithDraw;
	private ATMamount mockAtmAmount;
	private User mockUser;
	private Account mockAccount;

	@BeforeEach
	public void setup() {
		mockWithDraw = new Withdraw();
		mockAtmAmount = new ATMamount();
		mockAtmAmount.setFifty(10);
		mockAtmAmount.setTwenty(20);
		mockAtmAmount.setTen(20);
		mockAtmAmount.setFive(10);

		mockAccount = new Account();
		mockAccount.setBalance(600);
		mockAccount.setOverdraft(200);

		Set<UserRole> mockRoles = new HashSet<>();
		mockRoles.add(UserRole.ROLE_USER);

		mockUser = new User();
		mockUser.setAccountnumber("123");
		mockUser.setPin(new BCryptPasswordEncoder().encode("123"));
		mockUser.setRoles(mockRoles);
		mockUser.setAccount(mockAccount);
	}

	@Test
	void withdraw_happyPath() {
		Map<Integer, Integer> expected = new HashMap<>();
		expected.put(50, 10);
		expected.put(20, 5);
		expected.put(10, 0);
		expected.put(5, 0);
		Map<Integer, Integer> response = mockWithDraw.withdraw(mockAtmAmount, mockUser, 600, new StringBuilder());
		Assertions.assertThat(expected.toString()).isEqualTo(response.toString());
	}

	@Test
	void test_withdraw_atmAccount_validAmount() {
		Map<Integer, Integer> expected = new HashMap<>();
		expected.put(50, 10);
		expected.put(20, 20);
		expected.put(10, 20);
		expected.put(5, 10);
		mockAccount.setBalance(1150);
		mockUser.setAccount(mockAccount);
		Map<Integer, Integer> response = mockWithDraw.withdraw(mockAtmAmount, mockUser, 1150, new StringBuilder());
		Assertions.assertThat(expected.toString()).isEqualTo(response.toString());

	}

	@Test
	void test_withdraw_atmAccount_invalidAmount() {
		mockAccount.setBalance(1160);
		mockUser.setAccount(mockAccount);
		StringBuilder dummy_message = new StringBuilder();
		mockWithDraw.withdraw(mockAtmAmount, mockUser, 1160, dummy_message);
		String expected = "Available balance in ATM : 1150 but requested amount : 1160";
		Assertions.assertThat(expected).isEqualTo(dummy_message.toString());

	}

	@Test
	void test_withdraw_UserAccount_invalidAmount() {
		StringBuilder dummy_message = new StringBuilder();
		mockWithDraw.withdraw(mockAtmAmount, mockUser, 850, dummy_message);
		String expected = "Available balance in user account : 800 but requested amount : 850";
		Assertions.assertThat(expected).isEqualTo(dummy_message.toString());

	}
}