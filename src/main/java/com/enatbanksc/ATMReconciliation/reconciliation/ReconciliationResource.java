/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;

import com.enatbanksc.ATMReconciliation.branch.BranchService;
import com.enatbanksc.ATMReconciliation.enat.ENTransaction;
import com.enatbanksc.ATMReconciliation.enat.ENTransactionService;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransactionService;
import java.util.Calendar;
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

        Date newToDate = incrementDateByOne(toDate);

        return service.getReversals(
                eNTransactionService.getEntTransactionBetween(fromDate, toDate, branchId),
                eTSTransactionService.getTransactionsBetween(fromDate, newToDate));
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

    @GetMapping("/atm-transactions/{branchId}")
    public @ResponseBody
    List<ENTransaction> getAtmTransactions(
            @RequestParam("from_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @PathVariable String branchId) {

        return service.getATMTransactions(
                eNTransactionService.getEntTransactionBetween(fromDate, toDate, branchId),
//                 eTSTransactionService.getTransactionsBetween(fromDate, toDate, branchService.show(Integer.parseInt(branchId)).getTerminalId())
                eTSTransactionService.getTransactionsBetween(fromDate, toDate)
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

        Date newToDate = incrementDateByOne(toDate);
        return service.getClaims(
                eNTransactionService.getEntTransactionBetween(fromDate, toDate, branchId),
                eTSTransactionService.getTransactionsBetween(fromDate, newToDate, branchService.show(Integer.parseInt(branchId)).getTerminalId()));
    }

    private Date incrementDateByOne(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}
