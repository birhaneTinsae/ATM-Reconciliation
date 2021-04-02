/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransaction;
import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransactionRepository;
import java.util.List;
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
public class DBWriter implements ItemWriter<ETSTransaction> {

    private static final Logger LOG = LoggerFactory.getLogger(DBWriter.class);
    @Autowired
    private ETSTransactionRepository repository;

    /**
     *
     * @param list of etswitch transaction read from csv file
     * @throws Exception
     */
    @Override
    public void write(List<? extends ETSTransaction> list) throws Exception {
        LOG.debug("DATABASE WRITER", "writing to the database");
        repository.saveAll(list);
        LOG.debug("DATABASE WRITER", "writing to the database completed");
    }

}
