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
public class AlreadyAuthorizedException extends RuntimeException {

    public AlreadyAuthorizedException() {
    }

    public AlreadyAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyAuthorizedException(String message) {
        super(message);
    }
    
    
    
}
