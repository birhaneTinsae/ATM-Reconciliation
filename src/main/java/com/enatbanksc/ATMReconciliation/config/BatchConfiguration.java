/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.config;

import com.enatbanksc.ATMReconciliation.batch.ETSTransactionItemProcessor;
import com.enatbanksc.ATMReconciliation.batch.JobCompletionNotificationListener;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 *
 * @author btinsae
 */
@Configuration
public class BatchConfiguration {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    ResourceLoader resourceLoader;
    
    @Value("input/EB 05.03.2018 - member_reconcilation_report.csv.csv")
    Resource[] resources;

  

    @Bean
    public FlatFileItemReader<ETSTransaction> reader(/*@Value("${input}") Resource in*/) {
        return new FlatFileItemReaderBuilder<ETSTransaction>()
                .name("etstItemReader")
                .resource(new ClassPathResource("member_reconcilation_report.csv"))
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names(new String[]{"issuer", "acquirer", "MTI", "cardNumber", "amount", "currency", "transactionDate", "transactionDesc", "terminalId", "transactionPlace", "stan", "refnumF37", "authIdRespF38", "FeUtrnno", "BoUtrnno", "feeAmountOne", "feeAmountTwo"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ETSTransaction>() {
                    {
                        setTargetType(ETSTransaction.class);
                        setCustomEditors(Collections.singletonMap(Date.class,
                                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), false)));
                    }
                })
                .build();
    }

    @Bean
    public MultiResourceItemReader<ETSTransaction> mutiResourceItemReader() {
        MultiResourceItemReader<ETSTransaction> multiResourceItemReader = new MultiResourceItemReader<>();
        multiResourceItemReader.setResources(resources);
        multiResourceItemReader.setDelegate(reader());
        return multiResourceItemReader;
    }

    @Bean
    public ETSTransactionItemProcessor processor() {
        return new ETSTransactionItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<ETSTransaction> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ETSTransaction>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO ets_transactions ( issuer,  acquirer,  MTI,  card_number,  amount,  currency,  transaction_date,  transaction_desc,  terminal_id,  transaction_place,  stan,  refnumF37,  auth_Id_RespF38,  Fe_Utrnno,  Bo_Utrnno,  fee_Amount_One,  fee_Amount_Two) VALUES (:issuer,  :acquirer,  :MTI,  :cardNumber,  :amount,  :currency,  :transactionDate,  :transactionDesc,  :terminalId,  :transactionPlace,  :stan,  :refnumF37,  :authIdRespF38,  :FeUtrnno,  :BoUtrnno,  :feeAmountOne,  :feeAmountTwo)")
                .dataSource(dataSource)
                .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importETSTransactionsJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importETSTransactionsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<ETSTransaction> writer, Resource in) {
        return stepBuilderFactory.get("step1")
                .<ETSTransaction, ETSTransaction>chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
