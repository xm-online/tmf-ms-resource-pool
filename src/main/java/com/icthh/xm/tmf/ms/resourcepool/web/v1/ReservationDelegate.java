package com.icthh.xm.tmf.ms.resourcepool.web.v1;


import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.resourcepool.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.ReservationApiDelegate;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.Reservation;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.ReservationCreate;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class ReservationDelegate implements ReservationApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "ResourcepoolReserve", resolver = ProfileKeyResolver.class)
    @Override
    @PrivilegeDescription("Privilege to create reservation")
    public ResponseEntity<Reservation> createReservation(ReservationCreate reservation) {
        return ResponseEntity.ok().build();
    }

}
