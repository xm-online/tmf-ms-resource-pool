package com.icthh.xm.tmf.ms.resourcepool.config;


import com.icthh.xm.commons.lep.TenantScriptStorage;
import com.icthh.xm.commons.lep.spring.EnableLepServices;
import com.icthh.xm.commons.lep.spring.web.WebLepSpringConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
@EnableLepServices(basePackages = "com.icthh.xm.tmf.ms.resourcepool")
public class LepConfiguration extends WebLepSpringConfiguration {

    @Value("${application.lep.tenant-script-storage}")
    private TenantScriptStorage tenantScriptStorageType;

    public LepConfiguration(@Value("${spring.application.name}") String appName,
                            ApplicationEventPublisher eventPublisher,
                            ResourceLoader resourceLoader) {
        super(appName, eventPublisher, resourceLoader);
    }

    @Override
    protected TenantScriptStorage getTenantScriptStorageType() {
        return tenantScriptStorageType;
    }
}
