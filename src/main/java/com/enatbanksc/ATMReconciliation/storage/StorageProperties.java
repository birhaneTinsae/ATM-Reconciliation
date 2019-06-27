package com.enatbanksc.ATMReconciliation.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 *
 * @author btinsae
 * @version 1.0
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";//"C:\\Users\\btinsae\\Downloads\\OCTOBER\\xls\\";

    /**
     * to get file storage directory
     *
     * @return file storage directory
     */
    public String getLocation() {
        return location;
    }

    /**
     * to set file storage directory
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
