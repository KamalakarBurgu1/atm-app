package com.neueda.services.atm.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.neueda.services.atm.dao.ATMamount;
import com.neueda.services.atm.dao.User;
import com.neueda.services.atm.repository.UserRepository;
import com.neueda.services.atm.util.Withdraw;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ATMService atmService;

    private static final Log logger = LogFactory.getLog(UserService.class);
    
    @Autowired
    private Withdraw withdraw;

    public User findByAccountnumber(){
      
        return userRepository.findByAccountnumber(currentuser());
    }

    public String currentuser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("currentUser useranme interms of accountNUmber:::"+username);
        return username;
    }

    @Transactional
    public Map<Integer,Integer> withdraw(int withdrawAmount, String location, StringBuilder message) {
        User currentUser= userRepository.findByAccountnumber(currentuser());
        logger.info("currentUser in withdraw:::"+currentUser.getId());
        ATMamount atMamount = atmService.findByLocation(location);
        Map<Integer,Integer> dispenseAmount = withdraw.withdraw(atMamount, currentUser, withdrawAmount, message);
        userRepository.save(currentUser);
        atmService.save(atMamount);
        if(message.toString().equals(""))
            message.append("Available Balance after withdraw::"+ currentUser.getAccount().getBalance());
        return dispenseAmount;
    }
}
