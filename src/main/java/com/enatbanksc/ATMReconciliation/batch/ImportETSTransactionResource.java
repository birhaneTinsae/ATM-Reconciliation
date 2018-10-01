/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import java.util.Date;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author btinsae
 */
@RestController
@RequestMapping("/import-daily-est-data")
public class ImportETSTransactionResource {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping()
    public String importESTtransactions(
            @RequestParam("import_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date importDate) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            JobParameters jobParameters = new JobParametersBuilder().addDate("import_date", importDate)
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
