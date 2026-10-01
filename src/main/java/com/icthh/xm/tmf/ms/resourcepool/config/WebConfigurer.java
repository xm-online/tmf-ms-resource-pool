package com.icthh.xm.tmf.ms.resourcepool.config;

import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.h2.H2ConfigurationHelper;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.servlet.*;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    public WebConfigurer(Environment env, JHipsterProperties jHipsterProperties) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))) {
            initH2Console(servletContext);
        }
        log.info("Web application fully configured");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = withLegacyWildcardOrigin(jHipsterProperties.getCors());
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins())
            || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
        }
        return new CorsFilter(source);
    }

    /**
     * Spring 5.3+ rejects {@code allowed-origins: "*"} together with {@code allow-credentials: true}, which Spring 5.2
     * accepted (it echoed the request origin). The same configuration keeps working: the wildcard is moved to
     * {@code allowedOriginPatterns}, which answers with the request origin as before.
     */
    private static CorsConfiguration withLegacyWildcardOrigin(CorsConfiguration source) {
        List<String> origins = source.getAllowedOrigins();
        if (CollectionUtils.isEmpty(origins) || !origins.contains(CorsConfiguration.ALL)
            || !Boolean.TRUE.equals(source.getAllowCredentials())) {
            return source;
        }
        CorsConfiguration config = new CorsConfiguration(source);
        List<String> patterns = new ArrayList<>();
        if (source.getAllowedOriginPatterns() != null) {
            patterns.addAll(source.getAllowedOriginPatterns());
        }
        patterns.add(CorsConfiguration.ALL);
        List<String> rest = new ArrayList<>(origins);
        rest.remove(CorsConfiguration.ALL);
        config.setAllowedOrigins(rest.isEmpty() ? null : rest);
        config.setAllowedOriginPatterns(patterns);
        return config;
    }

    /**
     * Initializes H2 console.
     */
    private void initH2Console(ServletContext servletContext) {
        log.debug("Initialize H2 console");
        H2ConfigurationHelper.initH2Console(servletContext);
    }

}
