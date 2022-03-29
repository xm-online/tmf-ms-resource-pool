package com.icthh.xm.tmf.ms.resourcepool.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ReservationEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get reservation with limit and offset
     *
     * @param status            Reservation status
     * @param commerces         Possible reservation commerces
     * @param currentDate       Current date
     * @param isTimeoutNotified Is timeout notified
     * @param limit             Limit of max results
     * @return Reservations
     */
    public List<Reservation> findBy(Status status, List<Commerce> commerces, OffsetDateTime currentDate,
                                    Boolean isTimeoutNotified, Integer limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        cq.where(cb.equal(root.get(Reservation_.status), status),
            root.get(Reservation_.commerce).in(commerces),
            cb.lessThanOrEqualTo(root.get(Reservation_.createDate), currentDate),
            cb.equal(root.get(Reservation_.isTimeoutNotified), isTimeoutNotified));
        cq.select(root);
        return entityManager.createQuery(cq).setMaxResults(limit).getResultList();
    }

    /**
     * Get expired reservations
     *
     * @param status    Reservation status
     * @param commerces Possible reservation commerces
     * @param limit     Limit of max results
     * @return Reservations
     */
    public List<Reservation> findExpiredReservationsBy(Status status, List<Commerce> commerces, Integer limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        cq.where(cb.equal(root.get(Reservation_.status), status),
            root.get(Reservation_.commerce).in(commerces),
            cb.lessThanOrEqualTo(root.get(Reservation_.validFor), OffsetDateTime.now()));
        cq.select(root);
        return entityManager.createQuery(cq).setMaxResults(limit).getResultList();
    }
}
