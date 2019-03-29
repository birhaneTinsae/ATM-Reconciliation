/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.enat.transaction;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author btinsae
 */
@Entity
@Table(name = "EBVW_ATM_RECON", schema = "ebfcprod")
public class ENTransaction implements Serializable, Comparable<ENTransaction> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AC_ENTRY_SR_NO")
    private int id;
    private int stan;
    @Column(name = "AC_BRANCH")
    private String branch;
    @Column(name = "LCY_AMOUNT")
    private float amount;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "VALUE_DT")
    private Date transactionDate;
    
//    @Enumerated(EnumType.STRING)
//    @Column(name = "DRCR_IND")
//    private enum drcrInd
//    {
//        D,C
//    };
//    
    @Column(name = "TRN_CODE")
    private String transactionCode;

    @Override
    public int compareTo(ENTransaction o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStan() {
        return stan;
    }

    public void setStan(int stan) {
        this.stan = stan;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Override
    public String toString() {
        return "ENTransaction{" + "id=" + id + ", stan=" + stan + ", branch=" + branch + ", amount=" + amount + ", transactionDate=" + transactionDate + ", transactionCode=" + transactionCode + '}';
    }

}
