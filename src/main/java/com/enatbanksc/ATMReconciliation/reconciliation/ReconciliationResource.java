/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;

import com.enatbanksc.ATMReconciliation.branch.BranchService;
import com.enatbanksc.ATMReconciliation.enat.transaction.ENTransaction;
import com.enatbanksc.ATMReconciliation.enat.transaction.ENTransactionService;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransactionService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author btinsae
 */
@RestController
@RequestMapping("/reconcile")
public class ReconciliationResource {

    @Autowired
    private ReconciliationService service;
    @Autowired
    private ETSTransactionService eTSTransactionService;
    @Autowired
    private ENTransactionService eNTransactionService;
    @Autowired
    private BranchService branchService;

    @GetMapping("/reversals/{branchId}")
    public @ResponseBody
    List<ENTransaction> getReversals(
            @RequestParam("from_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @PathVariable String branchId) {

        return service.getReversals(
                eNTransactionService.getEntTransactionBetween(fromDate, toDate, branchId),
                eTSTransactionService.getTransactionsBetween(fromDate, toDate, branchService.show(Integer.parseInt(branchId)).getTerminalId()));
    }

    @GetMapping("/posts/{branchId}")
    public @ResponseBody
    List<ETSTransaction> getPosts(
            @RequestParam("from_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @PathVariable String branchId) {

        return service.getPosts(
                eNTransactionService.getEntTransactionBetween(fromDate, toDate, branchId),
                eTSTransactionService.getTransactionsBetween(fromDate, toDate, branchService.show(Integer.parseInt(branchId)).getTerminalId())
        );

    }

    @GetMapping("/claims/{branchId}")
    public @ResponseBody
    List<ENTransaction> getClaims(
            @RequestParam("from_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @PathVariable String branchId) {
        return service.getClaims(
                eNTransactionService.getEntTransactionBetween(fromDate, toDate, branchId),
                eTSTransactionService.getTransactionsBetween(fromDate, toDate, branchService.show(Integer.parseInt(branchId)).getTerminalId()));
    }

}