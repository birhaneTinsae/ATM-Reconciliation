/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.enat.transaction;

import com.enatbanksc.ATMReconciliation.utils.Common;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author btinsae
 */
@RestController
@RequestMapping("/enat-transaction")
public class ENTransactionResource implements Common<ENTransaction> {

    @Autowired
    ENTransactionService service;

    @PostMapping
    @Override
    public @ResponseBody
    ENTransaction store(@RequestBody ENTransaction t) {
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

}
