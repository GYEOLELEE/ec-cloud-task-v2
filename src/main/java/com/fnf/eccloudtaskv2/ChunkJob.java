package com.fnf.eccloudtaskv2;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ChunkJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("domainDatasource")
    private DataSource domainDatasource;

    @Autowired
    @Qualifier("domainTransactionManager")
    private PlatformTransactionManager transactionManager;


    @Bean
    public Job chunkTest() {
        return jobBuilderFactory.get("chunkTest")
                .incrementer(new RunIdIncrementer())
                .start(chunkTestStep())
                .build();
    }

    @Bean
    public Step chunkTestStep() {
//        }
        return stepBuilderFactory.get("chunkTestStep")
                .<SourceData, TargetData>chunk(3)
                .reader(sourceDataReader())
                .processor(sourceDataProcessor())
                .writer(targetDataWriter())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public ItemReader<SourceData> sourceDataReader() {
        String sql = "SELECT id, text, flag FROM source WHERE flag = false order by id";
        ItemReader<SourceData> item = new JdbcCursorItemReaderBuilder<SourceData>()
                .dataSource(domainDatasource)
                .sql(sql)
                .rowMapper(new BeanPropertyRowMapper<>(SourceData.class))
                .name("sourceDataReader")
                .build();
        System.out.println(item);
        return item;
    }

    @Bean
    public ItemProcessor<SourceData, TargetData> sourceDataProcessor() {
        return item -> {
            System.out.println("Processing item: " + item.toString());
            Thread.sleep(1000);
            return new TargetData(item.getId(), item.getText());
        };
    }

    @Bean
    public ItemWriter<TargetData> targetDataWriter() {
        return items -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(domainDatasource);
            for (TargetData item : items) {
                jdbcTemplate.update("INSERT INTO target (id, text) VALUES (?, ?)",
                        item.getId(), item.getText());
                jdbcTemplate.update("UPDATE source set flag = true where id = ?",
                        item.getId());
            }
        };
    }
}
