/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.exceptions;

/**
 *
 * @author birhane
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
