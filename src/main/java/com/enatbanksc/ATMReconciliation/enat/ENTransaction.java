/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.enat;

import lombok.Data;

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
@Data
@Table(name = "EBVW_ATM_RECON", schema = "ebfcprod")
public class ENTransaction implements Serializable, Comparable<ENTransaction> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
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
    

    @Column(name = "TRN_CODE")
    private String transactionCode;

    @Override
    public int compareTo(ENTransaction o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

