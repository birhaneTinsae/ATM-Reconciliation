/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.config;

import com.enatbanksc.ATMReconciliation.batch.JobCompletionNotificationListener;
import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransaction;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;

import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransactionInput;
import com.enatbanksc.ATMReconciliation.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.MultiResourceItemReader;

import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.DataBinder;


/**
 * @author btinsae
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ResourceLoader resourceLoader;
    private final StorageProperties storageProperties;
    @Qualifier("mysqlTransactionManager")
    private final PlatformTransactionManager transactionManager;


    @Bean
    @JobScope
    public FlatFileItemReader<ETSTransactionInput> reader() {
        return new FlatFileItemReaderBuilder<ETSTransactionInput>()
                .name("transactionItemReader")
                .linesToSkip(4)
                .delimited()
                .names("issuer", "acquirer", "MTI", "cardNumber", "amount", "currency", "transactionDate", "transactionDesc", "terminalId", "transactionPlace", "stan", "refnumF37", "authIdRespF38", "FeUtrnno", "BoUtrnno", "feeAmountOne", "feeAmountTwo")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
//                    @Override
//                    protected void initBinder(DataBinder binder) {
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
//                        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
//                                    @Override
//                                    public void setAsText(String text) throws IllegalArgumentException {
//                                        if (StringUtils.isNotEmpty(text)) {
//                                            setValue(LocalDateTime.parse(text, formatter));
//                                        } else {
//                                            setValue(null);
//                                        }
//                                    }
//
//                                    @Override
//                                    public String getAsText() throws IllegalArgumentException {
//                                        Object date = getValue();
//                                        if (date != null) {
//                                            return formatter.format((LocalDateTime) date);
//                                        } else {
//                                            return "";
//                                        }
//                                    }
//                                });


                                setTargetType(ETSTransactionInput.class);
//                    setCustomEditors(Collections.singletonMap(LocalDateTime.class, new CustomDateEditor(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"), false))))
//                    ;


                }}).build();
//        FlatFileItemReader<ETSTransaction> reader = new FlatFileItemReader<>();
//        reader.setLinesToSkip(4);
//        reader.setStrict(true);
//        reader.setLineMapper(new DefaultLineMapper<>() {
//            {
//                setLineTokenizer(new DelimitedLineTokenizer() {
//                    {
//                        setNames("issuer", "acquirer", "MTI", "cardNumber", "amount", "currency", "transactionDate", "transactionDesc", "terminalId", "transactionPlace", "stan", "refnumF37", "authIdRespF38", "FeUtrnno", "BoUtrnno", "feeAmountOne", "feeAmountTwo");
//                    }
//                });
//                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
//                    {
//                        setTargetType(ETSTransaction.class);
////                        setCustomEditors(Collections.singletonMap(Date.class,
////                                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), false)));
//                    }
//                });
//            }
//        });
//        return reader;
    }

    @Bean
    @JobScope
    public MultiResourceItemReader<ETSTransactionInput> multiResourceItemReader() throws IOException {
        MultiResourceItemReader<ETSTransactionInput> multiResourceItemReader = new MultiResourceItemReader<>();
        multiResourceItemReader.setStrict(true);
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
    public Step loadETSTCSV(ItemWriter<ETSTransaction> itemWriter, ItemProcessor<ETSTransactionInput, ETSTransaction> itemProcessor/*JdbcBatchItemWriter<ETSTransaction> writer, Resource in*/) throws IOException {
        return stepBuilderFactory.get("load-ests-csv")
                .<ETSTransactionInput, ETSTransaction>chunk(100)
                .reader(multiResourceItemReader())
                .processor(itemProcessor/*processor()*/)
                .writer(itemWriter)
                .transactionManager(transactionManager)
                .build();
    }

    public Resource[] loadResources() throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                .getResources(/*storageProperties.getEjFiles()*/"file:C:/Users/btinsae/Desktop/atm-reconcilation/active/*.csv");
    }


}
