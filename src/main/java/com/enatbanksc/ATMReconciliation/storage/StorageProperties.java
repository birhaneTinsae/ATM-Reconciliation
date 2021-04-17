package com.enatbanksc.ATMReconciliation.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author btinsae
 * @version 1.0
 */
@ConfigurationProperties("storage")
@Data
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location;
    private String active;
    private String archive;
    private String ejFiles;

}
