package com.enatbanksc.ATMReconciliation.storage;

/**
 * Custom storage exception
 *
 * @author btinsae
 * @version 1.0
 */
public class StorageException extends RuntimeException {

    /**
     * Constructor with error message
     *
     * @param message
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and throwable
     *
     * @param message
     * @param cause
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
