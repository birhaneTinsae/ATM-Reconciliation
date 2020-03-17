/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.utils;

import com.enatbanksc.ATMReconciliation.enat.ENTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author btinsae
 * @version 1.0
 */

public class SortTransactions {

	/**
	 * To sort ATM transactions from Switch vendor
	 *
	 * @param transactions
	 * @return sorted Switch vendor transactions.
	 */
	public static List<ETSTransaction> sortETSTransactions(List<ETSTransaction> transactions) {

		if (transactions == null || transactions.isEmpty()) {
			return null;
		}
		Comparator<ETSTransaction> comparator = Comparator.comparingInt(ETSTransaction::getStan);
		Collections.sort(transactions, comparator);

		return transactions;
	}

	/**
	 * To sort ATM transactions from CBS
	 *
	 * @param transactions
	 * @return sorted CBS transactions.
	 */
	public static List<ENTransaction> sortENTransactions(List<ENTransaction> transactions) {
		if (transactions == null || transactions.isEmpty()) {
			return null;
		}
		Comparator<ENTransaction> comparator;
		comparator = Comparator.comparingInt(ENTransaction::getId);
		Collections.sort(transactions, comparator);
		return transactions;
	}

}
