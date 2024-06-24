package com.fnf.eccloudtaskv2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DomainDatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix ="domain.datasource")
    public DataSource domainDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "domainTransactionManager")
    public PlatformTransactionManager batchTransactionManager(@Qualifier("domainDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
