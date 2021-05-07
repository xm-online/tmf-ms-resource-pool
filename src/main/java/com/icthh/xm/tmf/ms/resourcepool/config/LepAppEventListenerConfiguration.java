package com.icthh.xm.tmf.ms.resourcepool.config;


import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.commons.CommonsService;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.tmf.ms.resourcepool.lep.XmMsLepProcessingApplicationListener;
import com.icthh.xm.tmf.ms.resourcepool.persistence.ReservationEntityRepository;
import com.icthh.xm.tmf.ms.resourcepool.persistence.repository.ReservationProfileRepository;
import com.icthh.xm.tmf.ms.resourcepool.persistence.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * The {@link LepAppEventListenerConfiguration} class.
 */
@Configuration
public class LepAppEventListenerConfiguration {

    @Bean
    XmMsLepProcessingApplicationListener buildLepProcessingApplicationListener(
        TenantConfigService tenantConfigService,
        @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate,
        CommonsService commonsService,
        PermissionCheckService permissionCheckService,
        KafkaTemplate<String, String> kafkaTemplate,
        ReservationRepository reservationRepository,
        ReservationEntityRepository reservationEntityRepository,
        ReservationProfileRepository reservationProfileRepository) {

        return new XmMsLepProcessingApplicationListener(
            tenantConfigService,
            restTemplate,
            commonsService,
            permissionCheckService,
            kafkaTemplate,
            reservationRepository,
            reservationEntityRepository,
            reservationProfileRepository);
    }
}
