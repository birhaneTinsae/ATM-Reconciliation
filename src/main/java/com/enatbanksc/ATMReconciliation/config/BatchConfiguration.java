/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.config;

import com.enatbanksc.ATMReconciliation.batch.JobCompletionNotificationListener;
import com.enatbanksc.ATMReconciliation.batch.TransactionInput;
import com.enatbanksc.ATMReconciliation.batch.TransactionOutput;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import com.enatbanksc.ATMReconciliation.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.MultiResourceItemReader;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
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
@RequiredArgsConstructor
@Log4j2
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ResourceLoader resourceLoader;


    @Value("${transaction.file}")
    private Resource[] resources;
    private final StorageProperties storageProperties;


    @Bean
    @JobScope
    public FlatFileItemReader<TransactionInput> reader() {
        FlatFileItemReader<TransactionInput> reader = new FlatFileItemReader<>();
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
                        setTargetType(TransactionInput.class);
                        setCustomEditors(Collections.singletonMap(LocalDateTime.class,
                                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), false)));
                    }
                });
//                setFieldSetMapper(new BeanWrapperFieldSetMapper<>(){
//                    @Override
//                    public void registerCustomEditors(PropertyEditorRegistry registry) {
//                        super.registerCustomEditors(registry);
//                    }
//                });
            }
        });
        return reader;
    }

    @Bean
    @JobScope
    public MultiResourceItemReader<TransactionInput> multiResourceItemReader() {
        MultiResourceItemReader<TransactionInput> multiResourceItemReader = new MultiResourceItemReader<>();
        multiResourceItemReader.setResources(loadResources());
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
    public Step loadETSTCSV(/*ItemWriter<ETSTransaction> itemWriter*/@Qualifier("jdbcWriter") ItemWriter<TransactionOutput> itemWriter, ItemProcessor<TransactionInput, TransactionOutput> itemProcessor/*JdbcBatchItemWriter<ETSTransaction> writer, Resource in*/) {
        return stepBuilderFactory.get("load-ests-csv")
                .<TransactionInput, TransactionOutput>chunk(100)
                .reader(multiResourceItemReader())
                .processor(itemProcessor/*processor()*/)
                .writer(itemWriter)
                .build();
    }

    @Bean(name = "jdbcWriter")
    public JdbcBatchItemWriter<TransactionOutput> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TransactionOutput>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO ets_transactions  (issuer, acquirer, MTI, cardNumber, amount, currency, transactionDate, transactionDesc, terminalId, transactionPlace, stan, refnumF37, authIdRespF38, FeUtrnno, BoUtrnno, feeAmountOne, feeAmountTwo) VALUES (:issuer, :acquirer,:MTI,:cardNumber,:amount,:currency,:transactionDate,:transactionDesc,:terminalId,:transactionPlace,:stan,:refnumF37, :authIdRespF38, :FeUtrnno, :BoUtrnno, :feeAmountOne, :feeAmountTwo)")
                .dataSource(dataSource)
                .build();
    }
//    @Bean
//    public Resource[] loadResources() {
//        try {
//
//            return ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
//                    .getResources("file:C:/Users/birhane/Documents/ATM/csv/clean/*.csv");//getResources("classpath:/input/*.csv");
//        } catch (IOException ex) {
//            Logger.getLogger(BatchConfiguration.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    public Resource[] loadResources() {
        try {

            return ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources(storageProperties.getActive());
        } catch (IOException ex) {
            log.error(ex);
        }
        return null;
    }


}
