///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.enatbanksc.ATMReconciliation.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.hibernate.cfg.AvailableSettings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.HashMap;
//
///**
// * @author btinsae
// */
//@Configuration
//@EnableJpaRepositories(basePackages = "com.enatbanksc.ATMReconciliation.enat",
//        entityManagerFactoryRef = "oracleEntityManagerFactory",
//        transactionManagerRef = "oracleTransactionManager")
//public class OracleConfigBackUp {
//
//
//    @Autowired
//    Environment env;
////
////    @Bean(name = "oracleDataSource")
////    @ConfigurationProperties(prefix = "oracle.datasource")
////    public DataSource oracleDataSource() {
////        HikariDataSource dataSource = new HikariDataSource();
////        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver"));
////        dataSource.setJdbcUrl(env.getProperty("oracle.datasource.jdbc-url"));
////        dataSource.setUsername(env.getProperty("oracle.datasource.username"));
////        dataSource.setPassword(env.getProperty("oracle.datasource.password"));
////      //  dataSource.setCatalog("*****");
////        /**
////         * HikariCP specific properties. Remove if you move to other connection pooling library.
////         **/
////        dataSource.addDataSourceProperty("cachePrepStmts", true);
////        dataSource.addDataSourceProperty("prepStmtCacheSize", 25000);
////        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 20048);
////        dataSource.addDataSourceProperty("useServerPrepStmts", true);
////        dataSource.addDataSourceProperty("initializationFailFast", true);
////        dataSource.setPoolName("ORACLE_HIKARICP_CONNECTION_POOL");
////
////        return dataSource;
////    }
//
//    @Bean(name = "oracleDataSource")
//    @ConfigurationProperties(prefix = "spring.oracle")
//    public HikariDataSource oracleDataSource() {
//        HikariConfig cpConfig = new HikariConfig();
//        cpConfig.setMaximumPoolSize(1);
//        cpConfig.setConnectionTestQuery("SELECT 1");
//
//// performance senstive settings
//        cpConfig.setMinimumIdle(0);
//        cpConfig.setConnectionTimeout(30000);
//        cpConfig.setIdleTimeout(35000);
//        cpConfig.setMaxLifetime(45000);
//        return new HikariDataSource(cpConfig);
////        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
////    @Bean(name = "oracleEntityManagerFactory")
////    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory() {
////        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
////        em.setDataSource(oracleDataSource());
////        em.setPersistenceUnitName("ORACLE");
////        em.setPackagesToScan("com.enatbanksc.ATMReconciliation.enat");
////        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
////        em.setJpaVendorAdapter(vendorAdapter);
////        HashMap<String, Object> properties = new HashMap<>();
////        properties.put(AvailableSettings.HBM2DDL_AUTO, env.getProperty("oracle.hibernate.hbm2ddl.auto"));
////        properties.put(AvailableSettings.DIALECT, env.getProperty("oracle.hibernate.dialect"));
////        em.setJpaPropertyMap(properties);
////        return em;
////    }
//
//
////    @Bean(name = "oracleTransactionManager")
////    public PlatformTransactionManager oracleTransactionManager() {
////        JpaTransactionManager transactionManager
////                = new JpaTransactionManager();
////        transactionManager.setEntityManagerFactory(
////                oracleEntityManagerFactory().getObject());
////        return transactionManager;
////    }
//
//    @Bean(name = "oracleEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(EntityManagerFactoryBuilder builder,
//                                                                             @Qualifier("oracleDataSource") DataSource oracleDataSource) {
//        return builder.dataSource(oracleDataSource).packages("com.enatbanksc.ATMReconciliation.enat")
//                .persistenceUnit("oracle").build();
//    }
////
////    @Bean(name = "oracleTransactionManager")
////    public PlatformTransactionManager oracleTransactionManager(
////            @Qualifier("oracleEntityManagerFactory") EntityManagerFactory oracleEntityManagerFactory) {
////        return new JpaTransactionManager(oracleEntityManagerFactory);
////    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean oracleEntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new
//                LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(oracleDataSource());
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(adapter);
//        em.setPackagesToScan("com.enatbanksc.ATMReconciliation.enat");
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto",
//                env.getProperty("oracle.hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.dialect",
//                env.getProperty("spring.oracle.hibernate.dialect"));
//        em.setJpaPropertyMap(properties);
//
//        return em;
//    }
//
//    //
//    // @Bean
//    //
//    // public DataSource oracleDataSource() {
//    // DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    // dataSource.setDriverClassName(env.getProperty("oracle.jdbc.driverClassName"));
//    // dataSource.setPassword(env.getProperty("oracle.jdbc.password"));
//    // dataSource.setUrl(env.getProperty("oracle.jdbc.url"));
//    // dataSource.setUsername(env.getProperty("oracle.jdbc.user"));
//    // return dataSource;
//    // }
//    //
//    @Bean
//    public PlatformTransactionManager oracleTransactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(oracleEntityManager().getObject());
//        return transactionManager;
//    }
//}
