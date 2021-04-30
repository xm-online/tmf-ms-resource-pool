package com.icthh.xm.tmf.ms.resourcepool.config;

import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.DefaultHealthIndicatorRegistry;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Needs for xm-scheduler
 */
@Configuration
public class CustomHealthIndicator {
    @Bean
    public CompositeHealthIndicator bindersHealthIndicator() {
        return new CompositeHealthIndicator(new OrderedHealthAggregator(),
            new DefaultHealthIndicatorRegistry());
    }
}
