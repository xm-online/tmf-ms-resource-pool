package com.icthh.xm.tmf.ms.resourcepool.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class RestTemplateConfiguration {

    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Used by LEP scripts as {@code lepContext.templates.rest}. Was declared in the removed Spring Security OAuth2
     * {@code SecurityConfiguration}; Spring Cloud LoadBalancer replaces Ribbon (ribbon.http.client.enabled is gone).
     */
    @Bean
    @Qualifier("loadBalancedRestTemplate")
    public RestTemplate loadBalancedRestTemplate(ObjectProvider<RestTemplateCustomizer> customizerProvider) {
        RestTemplate restTemplate = new RestTemplate();
        customizerProvider.ifAvailable(customizer -> {
            log.info("loadBalancedRestTemplate: using Spring Cloud LoadBalancer");
            customizer.customize(restTemplate);
        });
        return restTemplate;
    }
}
