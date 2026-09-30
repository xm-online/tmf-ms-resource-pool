package com.icthh.xm.tmf.ms.resourcepool.lep;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.api.BaseLepContext;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.tmf.ms.resourcepool.persistence.ReservationEntityRepository;
import com.icthh.xm.tmf.ms.resourcepool.persistence.repository.ReservationRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Keeps the names LEP scripts used with the xm-commons 2 bindings ({@link LepXmAccountMsConstants}):
 * {@code lepContext.services.tenantConfigService}, {@code services.permissionService},
 * {@code services.reservationRepository}, {@code services.reservationEntityRepository},
 * {@code templates.rest}, {@code templates.kafka}. {@code lepContext.commons} is set by xm-commons.
 */
public class LepContext extends BaseLepContext {

    public LepServices services;
    public LepTemplates templates;

    public static class LepServices {
        public TenantConfigService tenantConfigService;
        public PermissionCheckService permissionService;
        public ReservationRepository reservationRepository;
        public ReservationEntityRepository reservationEntityRepository;
    }

    public static class LepTemplates {
        public RestTemplate rest;
        public KafkaTemplate<String, String> kafka;
    }
}
