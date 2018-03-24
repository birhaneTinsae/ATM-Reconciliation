/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 *
 * @author btinsae
 */
//@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfig {

//    //HikariConfig config = new HikariConfig("/hikari.properties");
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build(); //new HikariDataSource(config);
//    }
}
