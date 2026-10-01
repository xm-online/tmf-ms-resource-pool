package com.icthh.xm.tmf.ms.resourcepool.web.errors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.icthh.xm.tmf.ms.resourcepool.ResourcepoolApp;
import com.icthh.xm.tmf.ms.resourcepool.config.SecurityBeanOverrideConfiguration;
import com.icthh.xm.tmf.ms.resourcepool.config.TenantConfigMockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import com.icthh.xm.commons.i18n.error.web.ExceptionTranslator;

/**
 * Error responses that must stay as they were before the migration (compared with master). The controller advices
 * and message converters of the application context are used in a standalone setup: the LEP interceptor would wait
 * for the tenant LEP configuration, which xm-config does not deliver in the tests.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, TenantConfigMockConfiguration.class, ResourcepoolApp.class})
class LegacyErrorResponseIntTest {

    @Autowired
    private LegacyErrorResponseAdvice legacyErrorResponseAdvice;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LegacyErrorTestController())
            .setControllerAdvice(legacyErrorResponseAdvice, exceptionTranslator)
            .setMessageConverters(handlerAdapter.getMessageConverters().toArray(new HttpMessageConverter[0]))
            .build();
    }

    @Test
    void unmappedPathIsNotFound() throws Exception {
        mockMvc.perform(get("/tmf-api/nothing"))
            .andExpect(status().isNotFound());
    }

    @Test
    void missingParameterKeepsSpring5Message() throws Exception {
        mockMvc.perform(get("/test/legacy-errors/param"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("error.validation"))
            .andExpect(jsonPath("$.fieldErrors[0].field").value("name"))
            .andExpect(jsonPath("$.fieldErrors[0].message").value("Required String parameter 'name' is not present"));
    }

    @Test
    void upstreamServerErrorKeepsItsStatus() throws Exception {
        mockMvc.perform(get("/test/legacy-errors/upstream"))
            .andExpect(status().is(HttpStatus.BAD_GATEWAY.value()))
            .andExpect(jsonPath("$.error").value("error.502"));
    }
}
