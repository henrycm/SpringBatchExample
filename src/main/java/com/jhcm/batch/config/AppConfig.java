package com.jhcm.batch.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class AppConfig
{
    @Resource
    private Environment env;

    @Bean
    BatchConfigurer configurer( @Qualifier("dataSource") DataSource dataSource )
    {
        return new DefaultBatchConfigurer( dataSource );
    }

    @Bean(name = "apiDataSource")
    public DataSource apiDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( env.getProperty( "storage.datasource.driverClassName" ) );
        dataSource.setUrl( env.getProperty( "storage.datasource.url" ) );
        dataSource.setUsername( env.getProperty( "storage.datasource.username" ) );
        dataSource.setPassword( env.getProperty( "storage.datasource.password" ) );
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( env.getProperty( "batch.datasource.driverClassName" ) );
        dataSource.setUrl( env.getProperty( "batch.datasource.url" ) );
        dataSource.setUsername( env.getProperty( "batch.datasource.username" ) );
        dataSource.setPassword( env.getProperty( "batch.datasource.password" ) );
        return dataSource;
    }

    @Bean
    public PropertiesFactoryBean sqlProps()
    {
        PropertiesFactoryBean res = new PropertiesFactoryBean();
        res.setLocation( new ClassPathResource( "sql.xml" ) );
        return res;
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean freemarkerConfig()
    {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath( "/templates/" );
        return bean;
    }
}
