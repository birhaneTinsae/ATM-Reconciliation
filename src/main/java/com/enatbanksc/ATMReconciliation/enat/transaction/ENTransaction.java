/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.enat.transaction;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author btinsae
 */
@Entity
@Getter
@Setter
public class ENTransaction implements Serializable, Comparable<ENTransaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int stan;

    @Override
    public int compareTo(ENTransaction o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
