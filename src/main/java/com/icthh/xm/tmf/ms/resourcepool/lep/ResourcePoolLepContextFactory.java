package com.icthh.xm.tmf.ms.resourcepool.lep;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.api.BaseLepContext;
import com.icthh.xm.commons.lep.api.LepContextFactory;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.tmf.ms.resourcepool.persistence.ReservationEntityRepository;
import com.icthh.xm.tmf.ms.resourcepool.persistence.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Builds the LEP context with the same bindings the xm-commons 2 {@code XmMsLepProcessingApplicationListener} set.
 */
@Component
public class ResourcePoolLepContextFactory implements LepContextFactory {

    private final TenantConfigService tenantConfigService;
    private final RestTemplate restTemplate;
    private final PermissionCheckService permissionCheckService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ReservationRepository reservationRepository;
    private final ReservationEntityRepository reservationEntityRepository;

    public ResourcePoolLepContextFactory(TenantConfigService tenantConfigService,
                                         @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate,
                                         PermissionCheckService permissionCheckService,
                                         KafkaTemplate<String, String> kafkaTemplate,
                                         ReservationRepository reservationRepository,
                                         ReservationEntityRepository reservationEntityRepository) {
        this.tenantConfigService = tenantConfigService;
        this.restTemplate = restTemplate;
        this.permissionCheckService = permissionCheckService;
        this.kafkaTemplate = kafkaTemplate;
        this.reservationRepository = reservationRepository;
        this.reservationEntityRepository = reservationEntityRepository;
    }

    @Override
    public BaseLepContext buildLepContext(LepMethod lepMethod) {
        LepContext lepContext = new LepContext();
        lepContext.services = new LepContext.LepServices();
        lepContext.services.tenantConfigService = tenantConfigService;
        lepContext.services.permissionService = permissionCheckService;
        lepContext.services.reservationRepository = reservationRepository;
        lepContext.services.reservationEntityRepository = reservationEntityRepository;
        lepContext.templates = new LepContext.LepTemplates();
        lepContext.templates.rest = restTemplate;
        lepContext.templates.kafka = kafkaTemplate;
        return lepContext;
    }
}
