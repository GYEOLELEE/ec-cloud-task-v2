package com.fnf.eccloudtaskv2;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ChunkConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkJob() {
        return jobBuilderFactory.get("chunkJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .build();
    }

    @Bean
    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
                .<Integer, Integer>chunk(5)
                .reader(simpleReader())
                .processor(simpleProcessor())
                .writer(simpleWriter())
                .build();
    }

    @Bean
    public ItemReader<Integer> simpleReader() {
        return new ItemReader<>() {
            private final List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            private int index = 0;

            @Override
            public Integer read() {
                if (index < data.size()) {
                    return data.get(index++);
                }
                return null;
            }
        };
    }

    @Bean
    public ItemProcessor<Integer, Integer> simpleProcessor() {
        return item -> item + 1;
    }

    @Bean
    public ItemWriter<Integer> simpleWriter() {
        return items -> items.forEach(item -> System.out.println("Writing item: " + item));
    }
}
