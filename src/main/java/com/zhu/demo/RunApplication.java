package com.zhu.demo;

import com.zhu.demo.common.ApplicationContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.zhu.demo.dao")
public class RunApplication implements TransactionManagementConfigurer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RunApplication.class, args);
        new ApplicationContextHolder().setApplicationContext(context);
    }

    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return null;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(
            DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }
}
