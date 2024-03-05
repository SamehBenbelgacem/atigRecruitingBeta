package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.CandidateAdditionalInfosDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.CandidateAdditionalInfos}.
 */
public interface CandidateAdditionalInfosService {
    /**
     * Save a candidateAdditionalInfos.
     *
     * @param candidateAdditionalInfosDTO the entity to save.
     * @return the persisted entity.
     */
    CandidateAdditionalInfosDTO save(CandidateAdditionalInfosDTO candidateAdditionalInfosDTO);

    /**
     * Updates a candidateAdditionalInfos.
     *
     * @param candidateAdditionalInfosDTO the entity to update.
     * @return the persisted entity.
     */
    CandidateAdditionalInfosDTO update(CandidateAdditionalInfosDTO candidateAdditionalInfosDTO);

    /**
     * Partially updates a candidateAdditionalInfos.
     *
     * @param candidateAdditionalInfosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CandidateAdditionalInfosDTO> partialUpdate(CandidateAdditionalInfosDTO candidateAdditionalInfosDTO);

    /**
     * Get all the candidateAdditionalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidateAdditionalInfosDTO> findAll(Pageable pageable);

    /**
     * Get all the CandidateAdditionalInfosDTO where Candidate is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CandidateAdditionalInfosDTO> findAllWhereCandidateIsNull();

    /**
     * Get the "id" candidateAdditionalInfos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidateAdditionalInfosDTO> findOne(Long id);

    /**
     * Delete the "id" candidateAdditionalInfos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
