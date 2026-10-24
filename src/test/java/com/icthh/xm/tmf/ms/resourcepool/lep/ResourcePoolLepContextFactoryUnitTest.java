package com.icthh.xm.tmf.ms.resourcepool.lep;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.tmf.ms.resourcepool.persistence.ReservationEntityRepository;
import com.icthh.xm.tmf.ms.resourcepool.persistence.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * The LEP context keeps the bindings of the xm-commons 2 {@code XmMsLepProcessingApplicationListener}:
 * services (tenantConfigService, permissionService, reservationRepository, reservationEntityRepository)
 * and templates (rest, kafka).
 */
class ResourcePoolLepContextFactoryUnitTest {

    @Test
    @SuppressWarnings("unchecked")
    void buildsContextWithLegacyBindingNames() {
        TenantConfigService tenantConfigService = mock(TenantConfigService.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        PermissionCheckService permissionCheckService = mock(PermissionCheckService.class);
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        ReservationEntityRepository reservationEntityRepository = mock(ReservationEntityRepository.class);

        LepContext context = (LepContext) new ResourcePoolLepContextFactory(tenantConfigService, restTemplate,
            permissionCheckService, kafkaTemplate, reservationRepository, reservationEntityRepository)
            .buildLepContext(null);

        assertThat(context.services.tenantConfigService).isSameAs(tenantConfigService);
        assertThat(context.services.permissionService).isSameAs(permissionCheckService);
        assertThat(context.services.reservationRepository).isSameAs(reservationRepository);
        assertThat(context.services.reservationEntityRepository).isSameAs(reservationEntityRepository);
        assertThat(context.templates.rest).isSameAs(restTemplate);
        assertThat(context.templates.kafka).isSameAs(kafkaTemplate);
    }
}
