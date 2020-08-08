/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.config;

import com.enatbanksc.ATMReconciliation.batch.JobCompletionNotificationListener;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.MultiResourceItemReader;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;


/**
 * @author btinsae
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ResourceLoader resourceLoader;


    @Value("${transaction.file}")
    private  Resource[] resources;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ResourceLoader resourceLoader) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public FlatFileItemReader<ETSTransaction> reader() {
        FlatFileItemReader<ETSTransaction> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(4);
        reader.setStrict(false);
        reader.setLineMapper(new DefaultLineMapper<>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("issuer", "acquirer", "MTI", "cardNumber", "amount", "currency", "transactionDate", "transactionDesc", "terminalId", "transactionPlace", "stan", "refnumF37", "authIdRespF38", "FeUtrnno", "BoUtrnno", "feeAmountOne", "feeAmountTwo");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(ETSTransaction.class);
                        setCustomEditors(Collections.singletonMap(Date.class,
                                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), false)));
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public MultiResourceItemReader<ETSTransaction> multiResourceItemReader() {
        MultiResourceItemReader<ETSTransaction> multiResourceItemReader = new MultiResourceItemReader<>();
       // multiResourceItemReader.setResources(loadResources());
        multiResourceItemReader.setResources(resources);
        multiResourceItemReader.setDelegate(reader());
        return multiResourceItemReader;
    }


    @Bean
    public Job importETSTransactionsJob(JobCompletionNotificationListener listener, Step loadCSV) {
        return jobBuilderFactory.get("importETSTransactionsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(loadCSV)
                .end()
//                .start(loadCSV)
                .build();
    }

    @Bean
    public Step loadETSTCSV(ItemWriter<ETSTransaction> itemWriter, ItemProcessor<ETSTransaction, ETSTransaction> itemProcessor/*JdbcBatchItemWriter<ETSTransaction> writer, Resource in*/) {
        return stepBuilderFactory.get("load-ests-csv")
                .<ETSTransaction, ETSTransaction>chunk(100)
                .reader(multiResourceItemReader())
                .processor(itemProcessor/*processor()*/)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Resource[] loadResources() {
        try {

            return ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources("file:C:/Users/birhane/Documents/ATM/csv/clean/*.csv");//getResources("classpath:/input/*.csv");
        } catch (IOException ex) {
            Logger.getLogger(BatchConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PreDestroy
    public void preDestroy() {
        if (reader() != null) {
            reader().close();
        }
    }


}
