///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.enatbanksc.ATMReconciliation.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
///**
// *
// * @author btinsae
// */
//@Configuration
//@EnableJpaRepositories(basePackages = "com.enatbanksc.ATMReconciliation.enat", entityManagerFactoryRef = "oracleEntityManagerFactory", transactionManagerRef = "oracleTransactionManager")
//public class OracleConfig {
//
//	@Bean(name = "oracleDataSource")
//	@ConfigurationProperties(prefix = "oracle.datasource")
//	public HikariDataSource oracleDataSource() {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
//
//	@Bean(name = "oracleEntityManagerFactory")
//	public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(EntityManagerFactoryBuilder builder,
//			@Qualifier("oracleDataSource") DataSource oracleDataSource) {
//		return builder.dataSource(oracleDataSource).packages("com.enatbanksc.ATMReconciliation.enat")
//				.persistenceUnit("oracle").build();
//	}
//
//	@Bean(name = "oracleTransactionManager")
//	public PlatformTransactionManager oracleTransactionManager(
//			@Qualifier("oracleEntityManagerFactory") EntityManagerFactory oracleEntityManagerFactory) {
//		return new JpaTransactionManager(oracleEntityManagerFactory);
//	}
//
//	// @Autowired
//	// private Environment env;
//	//
//	//
//	// @Bean
//	//
//	// public LocalContainerEntityManagerFactoryBean oracleEntityManager() {
//	// LocalContainerEntityManagerFactoryBean em = new
//	// LocalContainerEntityManagerFactoryBean();
//	// em.setDataSource(oracleDataSource());
//	// HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//	// em.setJpaVendorAdapter(adapter);
//	// em.setPackagesToScan(new String[]{"com.enatbanksc.ATMReconciliation.enat"});
//	// HashMap<String, Object> properties = new HashMap<>();
//	// properties.put("hibernate.hbm2ddl.auto",
//	// env.getProperty("oracle.hibernate.hbm2ddl.auto"));
//	// properties.put("hibernate.dialect",
//	// env.getProperty("oracle.hibernate.dialect"));
//	// em.setJpaPropertyMap(properties);
//	//
//	// return em;
//	// }
//	//
//	// @Bean
//	//
//	// public DataSource oracleDataSource() {
//	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	// dataSource.setDriverClassName(env.getProperty("oracle.jdbc.driverClassName"));
//	// dataSource.setPassword(env.getProperty("oracle.jdbc.password"));
//	// dataSource.setUrl(env.getProperty("oracle.jdbc.url"));
//	// dataSource.setUsername(env.getProperty("oracle.jdbc.user"));
//	// return dataSource;
//	// }
//	//
//	// @Bean
//	// public PlatformTransactionManager oracleTransactionManager() {
//	// JpaTransactionManager transactionManager = new JpaTransactionManager();
//	// transactionManager.setEntityManagerFactory(oracleEntityManager().getObject());
//	// return transactionManager;
//	// }
//}
