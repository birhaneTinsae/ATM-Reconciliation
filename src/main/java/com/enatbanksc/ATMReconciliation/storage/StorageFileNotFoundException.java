package com.enatbanksc.ATMReconciliation.storage;

/**
 *
 * @author btinsae
 * @version 1.0
 */
public class StorageFileNotFoundException extends StorageException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Custom file not found exception with error message constructor
     *
     * @param message
     */
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    /**
     * file not found exception with error message and throwable constructor
     *
     * @param message
     * @param cause
     */
    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
