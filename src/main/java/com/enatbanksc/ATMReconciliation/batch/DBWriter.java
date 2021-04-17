/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransaction;
import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransactionRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Write transactions read from CSV file to database.
 *
 * @author btinsae
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class DBWriter implements ItemWriter<ETSTransaction> {

    private final ETSTransactionRepository repository;

    /**
     *
     * @param list of etswitch transaction read from csv file
     * @throws Exception
     */
    @Override
    public void write(List<? extends ETSTransaction> list) throws Exception {
        log.debug("DATABASE WRITER", "writing to the database");
        repository.saveAll(list);
        log.debug("DATABASE WRITER", "writing to the database completed");
    }

}
