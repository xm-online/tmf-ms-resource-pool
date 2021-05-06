package com.icthh.xm.tmf.ms.resourcepool.persistence;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ReservationRepositoryCriteria {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get reservation with limit and offset//To do desc
     *
     * @param status
     * @return
     */
    public List<Reservation> findBy(Status status, OffsetDateTime currentDate, Boolean isTimeoutNotified, Integer limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        cq.where(cb.equal(root.get("status"), status), cb.lessThanOrEqualTo(root.get("createDate"), currentDate), cb.equal(root.get("isTimeoutNotified"), isTimeoutNotified));
        cq.select(root);
        return entityManager.createQuery(cq).setMaxResults(limit).getResultList();
    }

    /**
     * Get expired reservations
     *
     * @param status
     * @return
     */
    public List<Reservation> findExpiredReservationsBy(Status status, Integer limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        cq.where(cb.equal(root.get("status"), status), cb.lessThanOrEqualTo(root.get("validFor"), LocalDateTime.now()));
        cq.select(root);
        return entityManager.createQuery(cq).setMaxResults(limit).getResultList();
    }
}
