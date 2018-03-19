/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch.transaction;

import com.enatbanksc.ATMReconciliation.utils.Common;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author btinsae
 */
public class ETSTransactionResource implements Common<ETSTransaction> {

    @Autowired
    ETSTransactionService service;

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

    @PutMapping()
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

    @GetMapping()
    @Override
    public @ResponseBody
    List<ETSTransaction> getAll() {
        return service.getAll();
    }

}
