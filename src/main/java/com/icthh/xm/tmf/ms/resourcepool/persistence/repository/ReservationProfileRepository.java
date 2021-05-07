package com.icthh.xm.tmf.ms.resourcepool.persistence.repository;


import com.icthh.xm.tmf.ms.resourcepool.persistence.ReservationProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationProfileRepository extends JpaRepository<ReservationProfile, Long> {
    ReservationProfile findByInitiatorMsisdn(String initiatorMsisdn);
}
