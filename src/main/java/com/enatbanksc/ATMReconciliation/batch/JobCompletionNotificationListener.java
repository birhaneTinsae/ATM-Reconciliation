/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;


import com.enatbanksc.ATMReconciliation.etswitch.ETSTransactionService;
import com.enatbanksc.ATMReconciliation.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author btinsae
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final StorageProperties storageProperties;


    @SneakyThrows
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("!!! JOB FINISHED! Time to verify the results");
                FileSystemUtils.copyRecursively(Path.of(storageProperties.getActive()),
                        Path.of(storageProperties.getArchive()));
                Files.walk(Path.of(storageProperties.getActive()))
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
    }
}
