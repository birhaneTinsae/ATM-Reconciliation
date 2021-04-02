
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author btinsae
 */
@Controller()
@RequestMapping("/upload")
@Log4j2
@RequiredArgsConstructor
public class ViewController {
    private final ETSTransactionService service;

    @GetMapping("/new")
    public String uploadForm(Model model) {
        model.addAttribute("loaded_transaction_dates", service.loadedTransactionsDate());
        return "file_upload";
    }
}
