/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author btinsae
 */
@RestController
@RequestMapping("/import-daily-est-data")
public class ImportETSTransactionResource {

    @Autowired
    //@Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping()
    public String importESTtransactions() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (JobParametersInvalidException e) {
            logger.info(e.getMessage());
        } catch (JobExecutionAlreadyRunningException e) {
            logger.info(e.getMessage());
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.info(e.getMessage());
        } catch (JobRestartException e) {
            logger.info(e.getMessage());
        }

        return "Done";
    }
}
