package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.CandidateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.Candidate}.
 */
public interface CandidateService {
    /**
     * Save a candidate.
     *
     * @param candidateDTO the entity to save.
     * @return the persisted entity.
     */
    CandidateDTO save(CandidateDTO candidateDTO);

    /**
     * Updates a candidate.
     *
     * @param candidateDTO the entity to update.
     * @return the persisted entity.
     */
    CandidateDTO update(CandidateDTO candidateDTO);

    /**
     * Partially updates a candidate.
     *
     * @param candidateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CandidateDTO> partialUpdate(CandidateDTO candidateDTO);

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidateDTO> findAll(Pageable pageable);

    /**
     * Get all the candidates with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidateDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" candidate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidateDTO> findOne(Long id);

    /**
     * Delete the "id" candidate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
