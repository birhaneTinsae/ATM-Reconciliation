package com.enatbanksc.ATMReconciliation.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:persistence-multiple-db-boot.properties"})
@EnableJpaRepositories(basePackages = "com.enatbanksc.ATMReconciliation.enat", entityManagerFactoryRef = "oracleEntityManager", transactionManagerRef = "oracleTransactionManager")
public class OracleConfiguration {
    private final Environment env;

    public OracleConfiguration(Environment env) {
        super();
        this.env = env;
    }

    //

    @Bean
    public LocalContainerEntityManagerFactoryBean oracleEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(oracleDataSource());
        em.setPackagesToScan("com.enatbanksc.ATMReconciliation.enat");
        em.setPersistenceUnitName("ORACLE");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",env.getProperty("spring.second-datasource.hibernate.dialect") /*"org.hibernate.dialect.Oracle10gDialect"*/);
        em.setJpaPropertyMap(properties);

        return em;
    }
    // @Bean
    // @ConfigurationProperties(prefix = "spring.datasource.hikari")
    // public HikariConfig hikariConfig() {
    //     return new HikariConfig();
    // }
    @Bean
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource oracleDataSource() {
        // return new HikariDataSource(hikariConfig());
       return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager oracleTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(oracleEntityManager().getObject());
        return transactionManager;
    }

}
