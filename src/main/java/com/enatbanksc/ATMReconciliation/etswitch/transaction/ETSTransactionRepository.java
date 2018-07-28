/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch.transaction;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author btinsae
 */
@Repository
public interface ETSTransactionRepository extends JpaRepository<ETSTransaction, Integer> {

    /**
     *
     * @param from
     * @param to
     * @param branch
     * @return
     */
    List<ETSTransaction> findByTransactionDateBetweenAndTerminalId(Date from, Date to,String branch);
    @Query("SELECT DATE(transactionDate) AS dateonly FROM ets_transactions GROUP BY date(transactionDate)")
    List<Date> loadedTransactionsByDate();
    List<ETSTransaction>findByTransactionDateBetween(Date from,Date to);
    
}
