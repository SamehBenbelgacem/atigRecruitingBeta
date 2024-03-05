package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.ObjectContainingFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.ObjectContainingFile}.
 */
public interface ObjectContainingFileService {
    /**
     * Save a objectContainingFile.
     *
     * @param objectContainingFileDTO the entity to save.
     * @return the persisted entity.
     */
    ObjectContainingFileDTO save(ObjectContainingFileDTO objectContainingFileDTO);

    /**
     * Updates a objectContainingFile.
     *
     * @param objectContainingFileDTO the entity to update.
     * @return the persisted entity.
     */
    ObjectContainingFileDTO update(ObjectContainingFileDTO objectContainingFileDTO);

    /**
     * Partially updates a objectContainingFile.
     *
     * @param objectContainingFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObjectContainingFileDTO> partialUpdate(ObjectContainingFileDTO objectContainingFileDTO);

    /**
     * Get all the objectContainingFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObjectContainingFileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" objectContainingFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObjectContainingFileDTO> findOne(Long id);

    /**
     * Delete the "id" objectContainingFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
