package com.icthh.xm.tmf.ms.resourcepool.persistence.repository;

import com.icthh.xm.tmf.ms.resourcepool.persistence.Reservation;
import com.icthh.xm.tmf.ms.resourcepool.persistence.Status;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
     * Get reservation by code
     *
     * @param reservationCode
     * @return
     */
    @EntityGraph(attributePaths = {"reservationProfile"})
    Reservation findByReservationCode(String reservationCode);


    /**
     * Get reservations by initiatorMsisdn
     *
     * @param reservationProfileId
     * @return
     */
    List<Reservation> findByReservationProfileIdAndCreateDateGreaterThanEqual(Long reservationProfileId, OffsetDateTime date);


    /**
     * Get reservations by initiatorMsisdn
     *
     * @param reservationProfileId
     * @return
     */
    List<Reservation> findByReservationProfileId(Long reservationProfileId);

    @Query(value = "SELECT SCHEMA.reservation_code_sequence.nextval FROM dual", nativeQuery = true)
    BigDecimal nextReservationCode();
}
