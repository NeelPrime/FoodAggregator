package com.harman.foodaggregator.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    /**
     * Build a RestTemplate Bean with the default configuration
     */
    @Bean
    fun restTemplate():RestTemplate {
        return RestTemplateBuilder().build()
    }
}