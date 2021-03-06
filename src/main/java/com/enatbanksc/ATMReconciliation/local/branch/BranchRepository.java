/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.local.branch;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author btinsae
 */
@Repository
public interface BranchRepository extends PagingAndSortingRepository<Branch, Integer> {
    Branch findByTerminalId(String terminalId);
    Optional<Branch> findByCode(String code);
}
