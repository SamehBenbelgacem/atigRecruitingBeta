package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Offer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class OfferRepositoryWithBagRelationshipsImpl implements OfferRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Offer> fetchBagRelationships(Optional<Offer> offer) {
        return offer.map(this::fetchTags);
    }

    @Override
    public Page<Offer> fetchBagRelationships(Page<Offer> offers) {
        return new PageImpl<>(fetchBagRelationships(offers.getContent()), offers.getPageable(), offers.getTotalElements());
    }

    @Override
    public List<Offer> fetchBagRelationships(List<Offer> offers) {
        return Optional.of(offers).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Offer fetchTags(Offer result) {
        return entityManager
            .createQuery("select offer from Offer offer left join fetch offer.tags where offer.id = :id", Offer.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Offer> fetchTags(List<Offer> offers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, offers.size()).forEach(index -> order.put(offers.get(index).getId(), index));
        List<Offer> result = entityManager
            .createQuery("select offer from Offer offer left join fetch offer.tags where offer in :offers", Offer.class)
            .setParameter("offers", offers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
