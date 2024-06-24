package com.fnf.eccloudtaskv2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TaskConfiguration {

    @Qualifier("springDatasource")
    @Autowired
    private DataSource datasource;
    @Bean
    public DefaultTaskConfigurer taskConfigurer() {
        return new DefaultTaskConfigurer(datasource);
    }
}
