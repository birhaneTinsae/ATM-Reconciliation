/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;


import com.enatbanksc.ATMReconciliation.storage.FileSystemStorageService;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

/**
 * @author btinsae
 */
@Component
@Log4j2
public class EJUtil {

    // private static final String EJ_URI = "C:\\Users\\btinsae\\Documents\\EJS\\";
    private static final String EJ_URI = "\\\\192.168.100.99\\ejbkp";//"E:\\EJ_BKP\\";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TRANSACTION_START_TAG = "*TRANSACTION START*";
    private static final String TRANSACTION_END_TAG = "TRANSACTION END";
    private static final String NOTES_TAKEN = "NOTES TAKEN";
    private static final String DATE_PLACEHOLDER = "*";
    @Autowired
    static ResourceLoader loader;

    @Autowired
    static FileSystemStorageService fileSystemStorage;
//    

    public static boolean isPaid(String branchEjUri, String branchEjDir, String stan, LocalDateTime transactionDate) {
        /**
         * build ejUri by concatenating
         */
        StringBuilder builder = new StringBuilder();
        String builderString = null;
        String temp = null;
        String line = null;

        Resource ej = null;
        log.debug(ejUri(branchEjDir, branchEjUri, transactionDate));
        try {
            ej = new UrlResource(ejUri(branchEjDir, branchEjUri, transactionDate));
        } catch (MalformedURLException ex) {
            Logger.getLogger(EJUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!ej.exists()) {
            log.debug("missing stan {} of transaction date {}", stan, transactionDate);
            return isPaid(branchEjUri, branchEjDir, stan, incrementDate(transactionDate));

        }


        if (ej.exists() && ej.isReadable()) {


                try (/*FileInputStream fis = new FileInputStream(ej);*/Scanner input = new Scanner(ej.getInputStream(), "UTF-8"); InputStreamReader file = new InputStreamReader(ej.getInputStream()); BufferedReader reader = new BufferedReader(file)) {
                    while ((line = reader.readLine()) != null) {
                        // System.out.println("LINE "+line);
                        input.nextLine();
                        /**
                         * * to check if the status is paid with the
                         * transaction start and end tag.
                         */
                        if (line.contains(TRANSACTION_START_TAG)) {
                            while (!(temp = reader.readLine()).contains(TRANSACTION_END_TAG) && input.hasNext()) {
                                input.nextLine();
                                // System.out.println(temp);
                                builder.append(temp);
                            }
                            builderString = builder.toString();

                            if (builderString.contains(stan) && builderString.contains(NOTES_TAKEN)) {
                                log.debug("stan {}", stan);
                                return true;
                            }

                        }
                        builder.delete(0, builder.length());
                        builderString = null;

                    }
                    return false;
                } catch (IOException ex) {
                    System.out.println(stan + "\t" + transactionDate);
                    System.out.println(ex);
                }
            }


        return false;
    }

    /**
     * To convert date to formatted string
     *
     * @param date
     * @return given date object return string representation of it.
     */
    private static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    /**
     * *
     * To build formatted EJ uri
     *
     * @param branchEjUri
     * @param transactionDate
     * @return the formated EJ uri replacing '*' from branchEjUri with the
     * string representation of transactionDate
     */
    private static String ejURI(String branchEjDir, String branchEjUri, Date transactionDate) {
        String dateString = dateToString(transactionDate);
        return EJ_URI.concat(branchEjDir.concat(branchEjUri.replace(DATE_PLACEHOLDER, dateString)));
    }

    private static URI ejUri(String branchEjDir, String branchEjUri, LocalDateTime transactionDate) {
        return Paths.get(EJ_URI, branchEjDir.concat(branchEjUri.replace(DATE_PLACEHOLDER, transactionDate.toString()))).toUri();
    }

    private static LocalDateTime incrementDate(LocalDateTime date) {
        return date.plusDays(1);
    }

}
