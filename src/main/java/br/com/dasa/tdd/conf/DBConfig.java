package br.com.dasa.tdd.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBConfig {

    @Bean(name = "sqliteDb")
    @ConfigurationProperties(prefix="spring.datasource") 
    public DataSource sqliteDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "sqlite") 
    public JdbcTemplate sqliteJdbcTemplate(@Qualifier("sqliteDb") DataSource sqliteDb) { 
        return new JdbcTemplate(sqliteDb); 
    } 
}
