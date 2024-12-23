package com.jongho.hobbytalk.api.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean(name = ["DataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun dataSource(): DataSource {
        return DataSourceBuilder
            .create()
            .build()
    }
}