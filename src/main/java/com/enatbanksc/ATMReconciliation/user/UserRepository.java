/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author birhane
 */
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByUsername(String username);
}
