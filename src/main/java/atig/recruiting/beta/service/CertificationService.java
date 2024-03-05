package atig.recruiting.beta.service;

import atig.recruiting.beta.service.dto.CertificationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link atig.recruiting.beta.domain.Certification}.
 */
public interface CertificationService {
    /**
     * Save a certification.
     *
     * @param certificationDTO the entity to save.
     * @return the persisted entity.
     */
    CertificationDTO save(CertificationDTO certificationDTO);

    /**
     * Updates a certification.
     *
     * @param certificationDTO the entity to update.
     * @return the persisted entity.
     */
    CertificationDTO update(CertificationDTO certificationDTO);

    /**
     * Partially updates a certification.
     *
     * @param certificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CertificationDTO> partialUpdate(CertificationDTO certificationDTO);

    /**
     * Get all the certifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CertificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" certification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CertificationDTO> findOne(Long id);

    /**
     * Delete the "id" certification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
