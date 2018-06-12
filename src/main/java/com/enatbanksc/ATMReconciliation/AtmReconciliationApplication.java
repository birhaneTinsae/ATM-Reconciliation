package com.enatbanksc.ATMReconciliation;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class AtmReconciliationApplication {

    public static void main(String[] args) {
       
        //System.setProperty("input", "file://" + new File("C:/Users/btinsae/Downloads/member_reconcilation_report.csv").getAbsolutePath());
       
        SpringApplication.run(AtmReconciliationApplication.class, args);
    }
}
