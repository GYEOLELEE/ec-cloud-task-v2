package com.fnf.eccloudtaskv2;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@EnableBatchProcessing //배치기능 활성화
@SpringBootApplication
//@EnableConfigurationProperties(TaskProperties.class)
public class EcCloudTaskV2Application {

    public static void main(String[] args) {
        SpringApplication.run(EcCloudTaskV2Application.class, args);
    }
}
