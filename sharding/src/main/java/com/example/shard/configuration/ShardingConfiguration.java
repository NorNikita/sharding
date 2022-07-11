package com.example.shard.configuration;

import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import org.apache.shardingsphere.underlying.common.config.DataSourceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class ShardingConfiguration {

//    @Bean("master")
//    @Primary
//    public DataSource createDataSource() throws FileNotFoundException, SQLException, IOException {
//        DataSource source = YamlMasterSlaveDataSourceFactory.createDataSource(
//                ResourceUtils.getFile("classpath:sharding-config.yml")
//
//        );
//        return source;
//    }
}
