package com.example.shard.configuration;

import com.example.shard.configuration.properties.DataNodeProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(DataNodeProperties.class)
public class ShardingConfiguration {


    @Bean(name = "yaml-mapper")
    public ObjectMapper getYamlObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }

}
