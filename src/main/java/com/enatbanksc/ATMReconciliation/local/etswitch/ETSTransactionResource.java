/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.local.etswitch;

import com.enatbanksc.ATMReconciliation.utils.Common;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author btinsae
 */
@RestController
@RequestMapping("ets-transaction")
@Log4j2
@RequiredArgsConstructor
public class ETSTransactionResource implements Common<ETSTransaction> {

   private final ETSTransactionService service;

    @PostMapping()
    @Override
    public @ResponseBody
    ETSTransaction store(@RequestBody ETSTransaction t) {
        return service.store(t);

    }

    @GetMapping("/{id}")
    @Override
    public @ResponseBody
    ETSTransaction show(@PathVariable int id) {
        return service.show(id);
    }

    @PutMapping
    @Override
    public @ResponseBody
    ETSTransaction update(@RequestBody ETSTransaction t) {
        return service.update(t);
    }

    @DeleteMapping("/{id}")
    @Override
    public boolean delete(@PathVariable int id) {
        return service.delete(id);
    }

    @GetMapping
    @Override
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<ETSTransaction> getAll() {
        return service.getAll();
    }

    @GetMapping("/etst-between-dates")
    public @ResponseBody
    List<ETSTransaction> getTransactionsBetweenDates(@RequestParam("from_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                     @RequestParam("to_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                                     String branchCode) {
        return service.getTransactionsBetween(fromDate, toDate, branchCode);
    }

    @GetMapping("/load-transactions-date")
    public @ResponseBody
    List<LocalDate> getLoadedETSTransactionDate() {
        return service.loadedTransactionsDate();
    }

}
