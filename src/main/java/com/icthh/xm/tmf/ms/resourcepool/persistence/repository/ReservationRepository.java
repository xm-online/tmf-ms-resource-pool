package com.icthh.xm.tmf.ms.resourcepool.persistence.repository;

import com.icthh.xm.tmf.ms.resourcepool.persistence.Reservation;
import com.icthh.xm.tmf.ms.resourcepool.persistence.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Reservation}
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Check if exits by reservedMsisdn
     *
     * @param reservedMsisdn
     * @return
     */
    boolean existsByReservedMsisdnAndStatus(String reservedMsisdn, Status status);

    /**
     * Get count reservations by initiatorMsisdn
     *
     * @param initiatorMsisdn
     * @return
     */
    long countByInitiatorMsisdnAndStatus(String initiatorMsisdn, Status status);


    /**gi
     * Get reservation by code
     *
     * @param reservationCode
     * @return
     */
    List<Reservation> findByReservationCode(String reservationCode);

}
