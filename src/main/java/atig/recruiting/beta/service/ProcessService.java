package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.ProcessDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.Process}.
 */
public interface ProcessService {
    /**
     * Save a process.
     *
     * @param processDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessDTO save(ProcessDTO processDTO);

    /**
     * Updates a process.
     *
     * @param processDTO the entity to update.
     * @return the persisted entity.
     */
    ProcessDTO update(ProcessDTO processDTO);

    /**
     * Partially updates a process.
     *
     * @param processDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcessDTO> partialUpdate(ProcessDTO processDTO);

    /**
     * Get all the processes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessDTO> findAll(Pageable pageable);

    /**
     * Get the "id" process.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessDTO> findOne(Long id);

    /**
     * Delete the "id" process.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
