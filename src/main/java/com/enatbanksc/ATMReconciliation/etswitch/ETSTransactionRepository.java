/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;
import java.util.Date;
import java.util.List;

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
    List<ETSTransaction> findByTransactionDateBetweenAndTerminalIdOrderByTransactionDate(Date from, Date to, String branch);
    @Query(value="SELECT DATE(transactionDate) AS dateonly FROM ets_transactions GROUP BY date(transactionDate) ORDER BY transactionDate DESC LIMIT 6",nativeQuery = true)
    List<Date> loadedTransactionsDate();
    List<ETSTransaction>findByTransactionDateBetweenOrderByTransactionDate(Date from,Date to);
    @Query(value="SELECT * FROM ets_transactions WHERE transactionDate>=:from and transactionDate<:to ORDER BY transactionDate",nativeQuery = true)    
    List<ETSTransaction>findByTransactionDateGreaterThanOrEqualAndTransactionDateLessThanOrderByTransactionDate(@Param("from") Date from,@Param("to") Date to);

    
}
