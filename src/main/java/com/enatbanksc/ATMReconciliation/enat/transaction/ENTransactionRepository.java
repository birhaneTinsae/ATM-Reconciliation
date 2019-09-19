/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.enat.transaction;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This will be implemented by spring boot to eNTransactionRepositoriy bean.
 *
 * @author btinsae
 */
@Repository
public interface ENTransactionRepository extends JpaRepository<ENTransaction, Integer> {

    List<ENTransaction> findByTransactionDateGreaterThanAndTransactionDateLessThanAndBranch(Date fromDate, Date toDate, String branch);
    List<ENTransaction> findByTransactionDateBetweenAndBranchOrderById(Date fromDate, Date toDate, String branch);
}
