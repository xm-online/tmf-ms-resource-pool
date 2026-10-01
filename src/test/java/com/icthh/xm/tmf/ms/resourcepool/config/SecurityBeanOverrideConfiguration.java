package com.icthh.xm.tmf.ms.resourcepool.config;

import static com.icthh.xm.commons.config.client.config.XmRestTemplateConfiguration.XM_CONFIG_REST_TEMPLATE;
import static org.mockito.Mockito.mock;

import com.icthh.xm.commons.security.jwt.TokenProvider;
import com.icthh.xm.tmf.ms.resourcepool.kafka.TopicInitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * Overrides UAA specific beans, so they do not interfere the testing.
 * This configuration must be included in {@code @SpringBootTest} in order to take effect.
 */
@Configuration
public class SecurityBeanOverrideConfiguration {

    @Bean
    @Primary
    public TokenProvider tokenProvider() {
        return mock(TokenProvider.class);
    }

    @Bean
    @Primary
    public RestTemplate loadBalancedRestTemplate() {
        return mock(RestTemplate.class);
    }

    @Bean(XM_CONFIG_REST_TEMPLATE)
    public RestTemplate xmConfigRestTemplate() {
        return mock(RestTemplate.class);
    }

    /**
     * Creates the Kafka topics on start: there is no broker in the tests.
     */
    @Bean
    public TopicInitService topicInitService() {
        return mock(TopicInitService.class);
    }
}
