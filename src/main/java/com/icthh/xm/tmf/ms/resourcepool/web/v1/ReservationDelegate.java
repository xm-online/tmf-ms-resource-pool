package com.icthh.xm.tmf.ms.resourcepool.web.v1;


import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.resourcepool.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.ReservationApiDelegate;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.Reservation;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.ReservationCreate;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.ReservationUpdate;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
@Slf4j
public class ReservationDelegate implements ReservationApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "CreateReservation", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'RESOURCE.RESERVATION.CREATE')")
    @Override
    @PrivilegeDescription("Privilege to create reservation")
    public ResponseEntity<Reservation> createReservation(ReservationCreate reservation) {
        log.info("Method createReservation is called, input {}", reservation);
        return ResponseEntity.ok().build();
    }

    @Timed
    @LogicExtensionPoint(value = "RetrieveReservation", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'RESOURCE.RESERVATION.RETRIVE')")
    @Override
    @PrivilegeDescription("Privilege to retrieve reservation")
    public ResponseEntity<List<Reservation>> retrieveReservation(String id) {
        log.info("Method retrieveReservation is called, id {}", id);
        return ResponseEntity.ok().build();
    }

    @Timed
    @LogicExtensionPoint(value = "UpdateReservation", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'RESOURCE.RESERVATION.UPDATE')")
    @Override
    @PrivilegeDescription("Privilege to update reservation")
    public ResponseEntity<Reservation> patchReservation(String id, ReservationUpdate reservation) {
        log.info("Method patchReservation is called, id {} ReservationUpdate{}", id, reservation);
        return ResponseEntity.ok().build();
    }
}
