/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.config;

import com.enatbanksc.ATMReconciliation.batch.JobCompletionNotificationListener;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 *
 * @author btinsae
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    ResourceLoader resourceLoader;

    // @Value("${file.ej}")
//    private final Resource[] resources=new Resource[]{
//           // resourceLoader.getResource("file:C:\\Users\\btinsae\\Downloads\\OCTOBER\\csv\\EB_**.csv")
//    ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("classpath:/directory/**/*-context.xml")        
//    
//    };
//            
//            new ClassPathResource[]{
//        new ClassPathResource("input//EB_*.csv")
//    };
//    @Bean
//    public FlatFileItemReader<ETSTransaction> reader(/*@Value("${input}") Resource in*/) {
//        return new FlatFileItemReaderBuilder<ETSTransaction>()
//                .name("etstItemReader")
//                //.resource(new ClassPathResource("input\\member_reconcilation_report.csv"))
//                .linesToSkip(1)
//                .delimited()
//                .delimiter(",")
//                .names(new String[]{"issuer", "acquirer", "MTI", "cardNumber", "amount", "currency", "transactionDate", "transactionDesc", "terminalId", "transactionPlace", "stan", "refnumF37", "authIdRespF38", "FeUtrnno", "BoUtrnno", "feeAmountOne", "feeAmountTwo"})
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<ETSTransaction>() {
//                    {
//                        setTargetType(ETSTransaction.class);
//                        setCustomEditors(Collections.singletonMap(Date.class,
//                                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), false)));
//                    }
//                })
//                .build();
//    }
    @Bean
    public FlatFileItemReader<ETSTransaction> reader() {
        FlatFileItemReader<ETSTransaction> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setStrict(false);
        reader.setLineMapper(new DefaultLineMapper<ETSTransaction>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"issuer", "acquirer", "MTI", "cardNumber", "amount", "currency", "transactionDate", "transactionDesc", "terminalId", "transactionPlace", "stan", "refnumF37", "authIdRespF38", "FeUtrnno", "BoUtrnno", "feeAmountOne", "feeAmountTwo"});//, "feeAmountOne", "feeAmountTwo"
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<ETSTransaction>() {
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
    public MultiResourceItemReader<ETSTransaction> mutiResourceItemReader() {
        MultiResourceItemReader<ETSTransaction> multiResourceItemReader = new MultiResourceItemReader<>();
        multiResourceItemReader.setResources(loadResources());
        multiResourceItemReader.setDelegate(reader());
        return multiResourceItemReader;
    }

//    @Bean
//    public ETSTransactionItemProcessor processor() {
//        return new ETSTransactionItemProcessor();
//    }
//    @Bean
//    public JdbcBatchItemWriter<ETSTransaction> writer(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<ETSTransaction>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO ets_transactions (  issuer,  acquirer,  MTI,  cardNumber,  amount,  currency,  transactionDate,  transactionDesc,  terminalId,  transactionPlace,  stan,  refnumF37,  authIdRespF38,  FeUtrnno,  BoUtrnno) VALUES (:issuer,  :acquirer,  :MTI,  :cardNumber,  :amount,  :currency,  :transactionDate,  :transactionDesc,  :terminalId,  :transactionPlace,  :stan,  :refnumF37,  :authIdRespF38,  :FeUtrnno,  :BoUtrnno)")
//                .dataSource(dataSource)
//                .build();
//    }
    // end::readerwriterprocessor[]
    // tag::jobstep[]
    @Bean
    public Job importETSTransactionsJob(JobCompletionNotificationListener listener, Step loadCSV) {
        return jobBuilderFactory.get("importETSTransactionsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                // .flow(step1)
                // .end()
                .start(loadCSV)
                .build();
    }

    @Bean
    public Step loadETSTCSV(ItemWriter<ETSTransaction> itemWriter, ItemProcessor<ETSTransaction, ETSTransaction> itemProcessor/*JdbcBatchItemWriter<ETSTransaction> writer, Resource in*/) {
        return stepBuilderFactory.get("load-ests-csv")
                .<ETSTransaction, ETSTransaction>chunk(100)
                .reader(mutiResourceItemReader())
                .processor(itemProcessor/*processor()*/)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Resource[] loadResources() {
        try {
            return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("file:C:/Users/btinsae/Downloads/OCTOBER/csv/clean/*.csv");//getResources("classpath:/input/*.csv");
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
