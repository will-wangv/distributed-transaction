package com.study.transaction.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner("order-service", "my_test_tx_group");
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    DruidDataSource druidDataSource(DataSourceProperties dataSourceProperties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        return druidDataSource;
    }

    @Bean("dataSourceProxy")
    public DataSourceProxyXA dataSourceProxyXA(DruidDataSource druidDataSource) {
//        return new DataSourceProxy(dataSource);
        return new DataSourceProxyXA(druidDataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSourceProxyXA dataSourceProxyXA) {
        return new JdbcTemplate(dataSourceProxyXA);
    }
}