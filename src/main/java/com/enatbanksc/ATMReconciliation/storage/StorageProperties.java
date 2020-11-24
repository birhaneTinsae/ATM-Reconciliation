package com.enatbanksc.ATMReconciliation.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 *
 * @author btinsae
 * @version 1.0
 */
@ConfigurationProperties("storage")
@Data
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";//"C:\\Users\\btinsae\\Downloads\\OCTOBER\\xls\\";

    @Value("${storage.active}")
    private String active;

    @Value("${storage.archive}")
    private String archive;

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
