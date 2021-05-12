package com.neueda.services.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neueda.services.atm.dao.User;

@Repository
public interface  UserRepository extends JpaRepository<User,Long> {
    User findByAccountnumber(String accountnumber);
}
