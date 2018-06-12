/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 *
 * @author btinsae
 */
@Component
public class EJUtil {

    private static final String EJ_URI = "C:/Users/btinsae/Documents/EJS/";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TRANSACTION_START_TAG = "*TRANSACTION START*";
    private static final String TRANSACTION_END_TAG = "TRANSACTION END";
    private static final String NOTES_TAKEN = "NOTES TAKEN";
    private static final String DATE_PLACEHOLDER = "*";
@Autowired
static ResourceLoader loader;
//@Autowired
//static Resource ej;
         
    public static boolean isPaid(String branchEjUri, String branchEjDir, String stan, Date transactionDate) {
        String ejUri = ejURI(branchEjDir, branchEjUri, transactionDate);
        StringBuilder builder = new StringBuilder();
        String builderString = null;
        String temp=null;
       // File ej = new File(ejUri);
        System.out.println("EJ URL\t"+ ejUri);
       // ej=loader.getResource("file:"+ejUri);
       File ej=new File(ejUri);
        System.out.println("ABSOLUT PATH "+ej.getAbsolutePath()+"STAN\t"+stan+"\t TRANSACTION DATE "+transactionDate);
        System.out.println("CAN READ\t"+ej.canRead());
        if (ej.canRead()) {

            try (FileInputStream fis=new FileInputStream(ej); Scanner input = new Scanner(fis, "UTF-8");) {
                while (input.hasNextLine() ) {
                    //System.out.println("TEST\t"+input.nextLine());
                    /**
                     * *
                     * to check if the status is paid with the transaction start
                     * and end tag.
                     */
                    if (input.nextLine().contains(TRANSACTION_START_TAG)) {
                        while ((temp=input.nextLine()).contains(TRANSACTION_END_TAG)) {
                           
                            builder.append(temp);
                        }
                        builderString = builder.toString();
                        
                        if (builderString.contains(stan) && builderString.contains(NOTES_TAKEN)) {
                            System.out.println("STAN "+stan);
                            return true;
                        }

                    }/*else{
                        return false;
                    }*/
                    builder.delete(0, builder.length());
                    builderString = null;

                }
            } catch (IOException ex) {
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
}
