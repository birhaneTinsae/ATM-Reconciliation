/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;


import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author btinsae
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    @Autowired
    ETSTransactionService service;
    //private final JdbcTemplate jdbcTemplate;

//    @Autowired
//    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOG.info("!!! JOB FINISHED! Time to verify the results");
            
            service.getAll().forEach(transaction -> LOG.info("Found <" + transaction + "> in the database."));
//
//            jdbcTemplate.query("SELECT * FROM ets_transactions",
//                    (rs, row) -> new ETSTransaction(
//                            rs.getString(12),
//                            rs.getString(5),
//                            rs.getInt(4),
//                            rs.getString(8),
//                            rs.getFloat(6),
//                            rs.getString(9),
//                            rs.getDate(16),
//                            rs.getString(17),
//                            rs.getString(15),
//                            rs.getString(18),
//                            rs.getInt(14),
//                            rs.getString(13),
//                            rs.getString(7),
//                            rs.getString(3),
//                            rs.getString(2)/*,
//                            rs.getFloat(10),
//                            rs.getFloat(11)*/)
//            ).forEach(transaction -> LOG.info("Found <" + transaction + "> in the database."));
        }
    }
}
