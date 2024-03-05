package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.ProcessStepDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.ProcessStep}.
 */
public interface ProcessStepService {
    /**
     * Save a processStep.
     *
     * @param processStepDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessStepDTO save(ProcessStepDTO processStepDTO);

    /**
     * Updates a processStep.
     *
     * @param processStepDTO the entity to update.
     * @return the persisted entity.
     */
    ProcessStepDTO update(ProcessStepDTO processStepDTO);

    /**
     * Partially updates a processStep.
     *
     * @param processStepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcessStepDTO> partialUpdate(ProcessStepDTO processStepDTO);

    /**
     * Get all the processSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessStepDTO> findAll(Pageable pageable);

    /**
     * Get the "id" processStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessStepDTO> findOne(Long id);

    /**
     * Delete the "id" processStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
