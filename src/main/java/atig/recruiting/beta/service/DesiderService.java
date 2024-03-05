package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.DesiderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.Desider}.
 */
public interface DesiderService {
    /**
     * Save a desider.
     *
     * @param desiderDTO the entity to save.
     * @return the persisted entity.
     */
    DesiderDTO save(DesiderDTO desiderDTO);

    /**
     * Updates a desider.
     *
     * @param desiderDTO the entity to update.
     * @return the persisted entity.
     */
    DesiderDTO update(DesiderDTO desiderDTO);

    /**
     * Partially updates a desider.
     *
     * @param desiderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DesiderDTO> partialUpdate(DesiderDTO desiderDTO);

    /**
     * Get all the desiders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DesiderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" desider.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DesiderDTO> findOne(Long id);

    /**
     * Delete the "id" desider.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
