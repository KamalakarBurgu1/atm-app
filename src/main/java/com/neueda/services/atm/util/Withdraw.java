package com.neueda.services.atm.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.dao.User;

@Component
public class Withdraw {

	private static final Log logger = LogFactory.getLog(Withdraw.class);
	private Map<Integer, Integer> dispenseAmount;
	private Map<Integer, Integer> atmnotes = new HashMap<>();
	private int atmBalance;
	private int reqamount;
	private int count = 20;
	private int divison = 50;
	private int processAmount;
	private int accountNumber;
	private boolean flag = true;

	public Map<Integer, Integer> withdraw(ATMamount atmamount, User user, int withdraw_amount, StringBuilder message) {
		atmnotes.put(50, atmamount.getFifty());
		atmnotes.put(20, atmamount.getTwenty());
		atmnotes.put(10, atmamount.getTen());
		atmnotes.put(5, atmamount.getFive());

		for (Map.Entry<Integer, Integer> set : atmnotes.entrySet()) {

			logger.info("atmnotes key:" + set.getKey() + "atmnotes value:" + set.getValue());

		}
		flag = true;
		dispenseAmount = new HashMap<>();
		dispenseAmount.put(50, 0);
		dispenseAmount.put(20, 0);
		dispenseAmount.put(10, 0);
		dispenseAmount.put(5, 0);
		this.reqamount = withdraw_amount;
		this.accountNumber = Integer.parseInt(user.getAccountnumber());
		this.processAmount = reqamount;
		atmBalance = (atmnotes.get(50) * 50) + (atmnotes.get(20) * 20) + (atmnotes.get(10) * 10)
				+ (atmnotes.get(5) * 5);
		if ((reqamount != 0) && (reqamount <= atmBalance)
				&& (reqamount <= (user.getAccount().getBalance() + user.getAccount().getOverdraft()))) {
			while (flag) {
				if (processAmount < divison) {
					if (processAmount >= count) {
						divison = count;
					} else {
						if (count == 20) {
							count = 10;
							divison = count;
						} else {
							count = 5;
							divison = count;
						}

					}

				}

				int divamount = (processAmount / divison);
				System.out.println("divamount:" + divamount);
				if ((divamount) <= atmnotes.get(divison) && (processAmount != 0) && processAmount >= divison) {
					dispenseAmount.put(divison, divamount);
					processAmount = processAmount - (dispenseAmount.get(divison) * divison);
					logger.info("current dispenseAmount:" + dispenseAmount.get(divison));
					logger.info("current processAmount:" + processAmount);
					System.out.println("divamount:" + divamount);
					int updatenotes = (atmnotes.get(divison) - divamount);
					System.out.println("updatenotes:" + updatenotes);
					System.out.println("reqamount:" + reqamount);
					atmnotes.put(divison, ((updatenotes < 0) ? 0 : updatenotes));
					user.getAccount().setBalance(user.getAccount().getBalance() - reqamount);
				} else {
					if (count == 20 && (processAmount != 0) && processAmount >= count) {
                        dispenseAmount.put(divison, atmnotes.get(divison));
						atmnotes.put(divison, 0);
						processAmount = processAmount - (dispenseAmount.get(divison) * divison);
						divison = count;
						count = 10;

					} else if (count == 10 && (processAmount != 0) && processAmount >= count) {
						dispenseAmount.put(divison, atmnotes.get(divison));
						atmnotes.put(divison, 0);
						processAmount = processAmount - (dispenseAmount.get(divison) * divison);
						divison = count;
						count = 5;
					} else if (count == 5 && (processAmount != 0)) {
						dispenseAmount.put(divison, atmnotes.get(divison));
						atmnotes.put(divison, 0);
						processAmount = processAmount - (dispenseAmount.get(divison) * divison);
						divison = count;
						count = 50;
					} else {
						flag = false;
						count = 20;
						divison = 50;
					}

				}

			}

		} else if (reqamount > (user.getAccount().getBalance() + user.getAccount().getOverdraft())) {
			message.append("Available balance in user account : "
					+ (user.getAccount().getBalance() + user.getAccount().getOverdraft()) + " but requested amount : "
					+ withdraw_amount);
		}

		else {
			message.append("Available balance in ATM : " + atmBalance + " but requested amount : " + withdraw_amount);
		}

		atmamount.setFifty(atmnotes.get(50));
		atmamount.setTwenty(atmnotes.get(20));
		atmamount.setTen(atmnotes.get(10));
		atmamount.setFive(atmnotes.get(5));
		System.out.println("Balance ATM account disburse amount final n fiftys:" + atmamount.getFifty());
		return dispenseAmount;
	}

}
