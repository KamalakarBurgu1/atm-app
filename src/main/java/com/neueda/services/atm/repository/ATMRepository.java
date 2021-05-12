package com.neueda.services.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neueda.services.atm.dao.ATMamount;

@Repository
public interface ATMRepository extends JpaRepository<ATMamount, Long> {
    ATMamount findByLocation(String location);
}
