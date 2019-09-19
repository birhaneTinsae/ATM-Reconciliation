/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.config;

/**
 * @author btinsae
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

/**
 *
 * @author btinsae
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.enatbanksc.ATMReconciliation",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager")
public class MysqlConfig {

    @Autowired
    private Environment env;

    //	@Primary
//	@Bean(name = "dataSource")
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public HikariDataSource dataSource() {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        //dataSource.setDriverClassName(env.getProperty("spring.datasource.driver"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
    //    dataSource.setCatalog(env.getProperty("spring.datasource.password"));
        /**
         * HikariCP specific properties. Remove if you move to other connection pooling library.
         **/
        dataSource.addDataSourceProperty("cachePrepStmts", true);
        dataSource.addDataSourceProperty("prepStmtCacheSize", 25000);
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 20048);
        dataSource.addDataSourceProperty("useServerPrepStmts", true);
        dataSource.addDataSourceProperty("initializationFailFast", true);
        dataSource.setPoolName("MYSQL_HIKARICP_CONNECTION_POOL");

        return dataSource;
    }

    @Bean(name = "mysqlEntityManagerFactory")
    @Primary //important
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mysqlDataSource());
        em.setPersistenceUnitName("MYSQL");
        em.setPackagesToScan("com.enatbanksc.ATMReconciliation.etswitch","com.enatbanksc.ATMReconciliation.user" ,"com.enatbanksc.ATMReconciliation.branch");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put(AvailableSettings.HBM2DDL_AUTO, env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put(AvailableSettings.DIALECT, env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

//	@Primary
//	@Bean(name = "mysqlEntityManagerFactory")
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
//			@Qualifier("dataSource") DataSource dataSource) {
//		return builder.dataSource(dataSource).packages(
//				new String[] { "com.enatbanksc.ATMReconciliation.etswitch", "com.enatbanksc.ATMReconciliation.branch" })
//				.persistenceUnit("mysql").build();
//	}

    @Primary
    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    // @Bean
    // @Primary
    // public LocalContainerEntityManagerFactoryBean mysqlEntityManager() {
    // LocalContainerEntityManagerFactoryBean em = new
    // LocalContainerEntityManagerFactoryBean();
    // em.setDataSource(mysqlDataSource());
    // HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    // em.setJpaVendorAdapter(adapter);
    // em.setPackagesToScan(new
    // String[]{"com.enatbanksc.ATMReconciliation.etswitch","com.enatbanksc.ATMReconciliation.branch"});
    // HashMap<String, Object> properties = new HashMap<>();
    // properties.put("hibernate.hbm2ddl.auto",
    // env.getProperty("mysql.hibernate.hbm2ddl.auto"));
    // properties.put("hibernate.dialect",
    // env.getProperty("mysql.hibernate.dialect"));
    // properties.put("hibernate.show-sql",
    // env.getProperty("mysql.hibernate.show_sql"));
    // em.setJpaPropertyMap(properties);
    //
    // return em;
    // }
    // public DataSource mysqlDataSource() {
    // DriverManagerDataSource dataSource = new DriverManagerDataSource();
    // dataSource.setDriverClassName(env.getProperty("mysql.jdbc.driverClassName"));
    // dataSource.setPassword(env.getProperty("mysql.jdbc.password"));
    // dataSource.setUrl(env.getProperty("mysql.jdbc.url"));
    // dataSource.setUsername(env.getProperty("mysql.jdbc.user"));
    // return dataSource;
    // }

    // @Bean
    // public PlatformTransactionManager mysqlTransactionManager() {
    // JpaTransactionManager transactionManager = new JpaTransactionManager();
    // transactionManager.setEntityManagerFactory(mysqlEntityManager().getObject());
    // return transactionManager;
    // }
}
