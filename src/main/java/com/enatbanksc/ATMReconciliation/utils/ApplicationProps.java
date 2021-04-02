package com.enatbanksc.ATMReconciliation.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
@Data
@Primary
public class ApplicationProps {

    private List<String> allowedOrigin;
    private double interestRate;
    private String incomeTaxGl;
    private String debitGl;
    private double taxRate;
    private String centralBranchCode;
    private List<String> currencies;


}



