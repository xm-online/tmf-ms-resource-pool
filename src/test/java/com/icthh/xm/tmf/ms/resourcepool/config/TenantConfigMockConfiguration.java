package com.icthh.xm.tmf.ms.resourcepool.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.icthh.xm.commons.config.client.repository.CommonConfigRepository;
import com.icthh.xm.commons.config.client.repository.TenantConfigRepository;
import com.icthh.xm.commons.config.client.repository.TenantListRepository;
import com.icthh.xm.commons.config.client.service.TenantAliasService;
import com.icthh.xm.commons.config.client.service.TenantConfigService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xm-config is disabled in the tests ({@code xm-config.enabled: false}): mocks of the config client beans.
 */
@Configuration
public class TenantConfigMockConfiguration {

    private final Set<String> tenants = new HashSet<>(Set.of("XM"));

    @Bean
    public TenantListRepository tenantListRepository() {
        TenantListRepository mockTenantListRepository = mock(TenantListRepository.class);

        doAnswer(mvc -> tenants.add(mvc.getArguments()[0].toString()))
            .when(mockTenantListRepository).addTenant(any());

        doAnswer(mvc -> tenants.remove(mvc.getArguments()[0].toString()))
            .when(mockTenantListRepository).deleteTenant(any());

        when(mockTenantListRepository.getTenants()).thenReturn(tenants);
        return mockTenantListRepository;
    }

    @Bean
    public TenantConfigRepository tenantConfigRepository() {
        return mock(TenantConfigRepository.class);
    }

    @Bean
    public TenantConfigService tenantConfigService() {
        return mock(TenantConfigService.class);
    }

    @Bean
    public CommonConfigRepository commonConfigRepository() {
        return mock(CommonConfigRepository.class);
    }

    @Bean
    public TenantAliasService testTenantAliasService() {
        return mock(TenantAliasService.class);
    }
}
