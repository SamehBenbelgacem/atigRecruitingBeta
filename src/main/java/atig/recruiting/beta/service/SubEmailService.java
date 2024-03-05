package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.SubEmailDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.SubEmail}.
 */
public interface SubEmailService {
    /**
     * Save a subEmail.
     *
     * @param subEmailDTO the entity to save.
     * @return the persisted entity.
     */
    SubEmailDTO save(SubEmailDTO subEmailDTO);

    /**
     * Updates a subEmail.
     *
     * @param subEmailDTO the entity to update.
     * @return the persisted entity.
     */
    SubEmailDTO update(SubEmailDTO subEmailDTO);

    /**
     * Partially updates a subEmail.
     *
     * @param subEmailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubEmailDTO> partialUpdate(SubEmailDTO subEmailDTO);

    /**
     * Get all the subEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubEmailDTO> findAll(Pageable pageable);

    /**
     * Get the "id" subEmail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubEmailDTO> findOne(Long id);

    /**
     * Delete the "id" subEmail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
