package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.EmailcredentialsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.Emailcredentials}.
 */
public interface EmailcredentialsService {
    /**
     * Save a emailcredentials.
     *
     * @param emailcredentialsDTO the entity to save.
     * @return the persisted entity.
     */
    EmailcredentialsDTO save(EmailcredentialsDTO emailcredentialsDTO);

    /**
     * Updates a emailcredentials.
     *
     * @param emailcredentialsDTO the entity to update.
     * @return the persisted entity.
     */
    EmailcredentialsDTO update(EmailcredentialsDTO emailcredentialsDTO);

    /**
     * Partially updates a emailcredentials.
     *
     * @param emailcredentialsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmailcredentialsDTO> partialUpdate(EmailcredentialsDTO emailcredentialsDTO);

    /**
     * Get all the emailcredentials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmailcredentialsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emailcredentials.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailcredentialsDTO> findOne(Long id);

    /**
     * Delete the "id" emailcredentials.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
