/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.branch;

import com.enatbanksc.ATMReconciliation.utils.Common;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * @author btinsae
 */
@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchResource implements Common<Branch> {

    private final BranchService service;

    @PostMapping()
    @Override
    public @ResponseBody
    Branch store(@RequestBody Branch t) {
        return service.store(t);
    }

    @GetMapping("/{id}")
    @Override
    public @ResponseBody
    Branch show(@PathVariable int id) {
        return service.show(id);
    }

    /**
     * @param t
     * @return
     */
    @PutMapping()
    @Override
    public @ResponseBody
    Branch update(@RequestBody Branch t) {
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
    List<Branch> getAll() {
        return service.getAll();
    }

    @Override
    public Page<Branch> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
