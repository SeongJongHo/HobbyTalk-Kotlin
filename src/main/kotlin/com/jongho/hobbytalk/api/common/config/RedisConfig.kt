package com.jongho.hobbytalk.api.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer


@Configuration
class RedisConfig {
    @Bean
    fun redisMessageListener(
        connectionFactory: RedisConnectionFactory?
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory!!)

        return container
    }

    @Bean
    fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory?): StringRedisTemplate {
        return StringRedisTemplate(redisConnectionFactory!!)
    }
}