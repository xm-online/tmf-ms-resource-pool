package com.icthh.xm.tmf.ms.resourcepool.lep;

import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_KEY_COMMONS;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_KEY_SERVICES;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_KEY_TEMPLATES;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_PERMISSION_SERVICE;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_RESERVATION_ENTITY_REPOSITORY;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_SERVICE_TENANT_CONFIG_SERVICE;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_TEMPLATE_KAFKA;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_TEMPLATE_REST;
import static com.icthh.xm.tmf.ms.resourcepool.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_RESERVATION_REPOSITORY;
import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.commons.CommonsExecutor;
import com.icthh.xm.commons.lep.commons.CommonsService;
import com.icthh.xm.commons.lep.spring.SpringLepProcessingApplicationListener;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.lep.api.ScopedContext;
import com.icthh.xm.tmf.ms.resourcepool.persistence.ReservationEntityRepository;
import com.icthh.xm.tmf.ms.resourcepool.persistence.repository.ReservationRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * The {@link XmMsLepProcessingApplicationListener} class.
 */
@RequiredArgsConstructor
public class XmMsLepProcessingApplicationListener extends SpringLepProcessingApplicationListener {

    private final TenantConfigService tenantConfigService;

    private final RestTemplate restTemplate;

    private final CommonsService commonsService;
    private final PermissionCheckService permissionCheckService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ReservationRepository reservationRepository;
    private final ReservationEntityRepository reservationEntityRepository;

    @Override
    protected void bindExecutionContext(ScopedContext executionContext) {
        // services
        Map<String, Object> services = new HashMap<>();
        services.put(BINDING_SUB_KEY_SERVICE_TENANT_CONFIG_SERVICE, tenantConfigService);
        services.put(BINDING_SUB_KEY_PERMISSION_SERVICE, permissionCheckService);
        services.put(BINDING_SUB_KEY_RESERVATION_REPOSITORY, reservationRepository);
        services.put(BINDING_SUB_KEY_RESERVATION_ENTITY_REPOSITORY, reservationEntityRepository);

        executionContext.setValue(BINDING_KEY_COMMONS, new CommonsExecutor(commonsService));
        executionContext.setValue(BINDING_KEY_SERVICES, services);

        // templates
        Map<String, Object> templates = new HashMap<>();
        templates.put(BINDING_SUB_KEY_TEMPLATE_REST, restTemplate);
        templates.put(BINDING_SUB_KEY_TEMPLATE_KAFKA, kafkaTemplate);

        executionContext.setValue(BINDING_KEY_TEMPLATES, templates);
    }

}
