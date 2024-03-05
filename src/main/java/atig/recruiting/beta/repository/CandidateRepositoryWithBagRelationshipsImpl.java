package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Candidate;
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
public class CandidateRepositoryWithBagRelationshipsImpl implements CandidateRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Candidate> fetchBagRelationships(Optional<Candidate> candidate) {
        return candidate.map(this::fetchTags);
    }

    @Override
    public Page<Candidate> fetchBagRelationships(Page<Candidate> candidates) {
        return new PageImpl<>(fetchBagRelationships(candidates.getContent()), candidates.getPageable(), candidates.getTotalElements());
    }

    @Override
    public List<Candidate> fetchBagRelationships(List<Candidate> candidates) {
        return Optional.of(candidates).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Candidate fetchTags(Candidate result) {
        return entityManager
            .createQuery(
                "select candidate from Candidate candidate left join fetch candidate.tags where candidate.id = :id",
                Candidate.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Candidate> fetchTags(List<Candidate> candidates) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, candidates.size()).forEach(index -> order.put(candidates.get(index).getId(), index));
        List<Candidate> result = entityManager
            .createQuery(
                "select candidate from Candidate candidate left join fetch candidate.tags where candidate in :candidates",
                Candidate.class
            )
            .setParameter("candidates", candidates)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
