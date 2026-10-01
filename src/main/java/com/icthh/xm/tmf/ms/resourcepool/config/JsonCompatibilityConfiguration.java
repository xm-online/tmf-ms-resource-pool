package com.icthh.xm.tmf.ms.resourcepool.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.icthh.xm.commons.i18n.error.domain.vm.ParameterizedErrorVM;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Keeps the JSON the service wrote with Jackson 2. Jackson 3 writes the properties bound by a constructor first,
 * so the xm-commons {@link ParameterizedErrorVM} (BusinessException body) came out as
 * error, error_description, params, requestId instead of error, error_description, requestId, params.
 */
@Configuration
public class JsonCompatibilityConfiguration {

    @Bean
    public JsonMapperBuilderCustomizer legacyPropertyOrderCustomizer() {
        return builder -> builder.addMixIn(ParameterizedErrorVM.class, ParameterizedErrorVMOrder.class);
    }

    @JsonPropertyOrder({"error", "error_description", "requestId", "params"})
    private abstract static class ParameterizedErrorVMOrder {
    }
}
