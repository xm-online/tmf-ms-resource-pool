package com.icthh.xm.tmf.ms.resourcepool.security;

import com.icthh.xm.commons.permission.access.ResourceFactory;
import org.springframework.stereotype.Component;

@Component
public class AppResourceFactory implements ResourceFactory {

    @Override
    public Object getResource(Object resourceId, String objectType) {
        return null;
    }
}
