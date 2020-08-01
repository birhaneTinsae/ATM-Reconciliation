/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.enat;

import com.enatbanksc.ATMReconciliation.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author btinsae
 */
//This means that this class is a Rest Controller
@RestController

// This means URL's start with /demo (after Application path)
@RequestMapping("/enat-transaction")
public class ENTransactionResource implements Common<ENTransaction> {

    // This means to get the bean called eNTransactionService
    @Autowired
    ENTransactionService service;

    @PostMapping
    @Override
    public @ResponseBody
        // @ResponseBody means the returned Object is the response, not a view name
    ENTransaction store(@RequestBody ENTransaction t) {

        /**
         * This return JSON representation of the stored object
         */
        return service.store(t);

    }

    @GetMapping("/{id}")
    @Override
    public @ResponseBody
    ENTransaction show(@PathVariable int id) {
        return service.show(id);
    }

    @PutMapping()
    @Override
    public @ResponseBody
    ENTransaction update(@RequestBody ENTransaction t) {
        return service.update(t);
    }

    @DeleteMapping("/{id}")
    @Override
    public boolean delete(@PathVariable int id) {
        return service.delete(id);
    }

    @GetMapping()
    @Override
    public @ResponseBody
    List<ENTransaction> getAll() {
        return service.getAll();
    }

    @GetMapping("/ent-between-dates")
    public @ResponseBody
    List<ENTransaction> getTransactionsBetweenDates(@RequestParam("from_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                    @RequestParam("to_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                                    String branchCode) {
        System.out.println(fromDate + "\t" + toDate);
        return service.getEntTransactionBetween(fromDate, toDate, branchCode);
    }

}
