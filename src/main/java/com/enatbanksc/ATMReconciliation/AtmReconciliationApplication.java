package com.enatbanksc.ATMReconciliation;



import com.enatbanksc.ATMReconciliation.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;



@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AtmReconciliationApplication {

    public static void main(String[] args) {
       
       
       
        SpringApplication.run(AtmReconciliationApplication.class, args);
    }
}
