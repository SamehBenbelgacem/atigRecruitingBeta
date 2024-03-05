package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.CertificationRepository;
import atig.recruiting.beta.service.CertificationQueryService;
import atig.recruiting.beta.service.CertificationService;
import atig.recruiting.beta.service.criteria.CertificationCriteria;
import atig.recruiting.beta.service.dto.CertificationDTO;
import atig.recruiting.beta.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link atig.recruiting.beta.domain.Certification}.
 */
@RestController
@RequestMapping("/api/certifications")
public class CertificationResource {

    private final Logger log = LoggerFactory.getLogger(CertificationResource.class);

    private static final String ENTITY_NAME = "certification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificationService certificationService;

    private final CertificationRepository certificationRepository;

    private final CertificationQueryService certificationQueryService;

    public CertificationResource(
        CertificationService certificationService,
        CertificationRepository certificationRepository,
        CertificationQueryService certificationQueryService
    ) {
        this.certificationService = certificationService;
        this.certificationRepository = certificationRepository;
        this.certificationQueryService = certificationQueryService;
    }

    /**
     * {@code POST  /certifications} : Create a new certification.
     *
     * @param certificationDTO the certificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificationDTO, or with status {@code 400 (Bad Request)} if the certification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CertificationDTO> createCertification(@RequestBody CertificationDTO certificationDTO) throws URISyntaxException {
        log.debug("REST request to save Certification : {}", certificationDTO);
        if (certificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new certification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CertificationDTO result = certificationService.save(certificationDTO);
        return ResponseEntity
            .created(new URI("/api/certifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /certifications/:id} : Updates an existing certification.
     *
     * @param id the id of the certificationDTO to save.
     * @param certificationDTO the certificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificationDTO,
     * or with status {@code 400 (Bad Request)} if the certificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificationDTO> updateCertification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CertificationDTO certificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Certification : {}, {}", id, certificationDTO);
        if (certificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CertificationDTO result = certificationService.update(certificationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, certificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /certifications/:id} : Partial updates given fields of an existing certification, field will ignore if it is null
     *
     * @param id the id of the certificationDTO to save.
     * @param certificationDTO the certificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificationDTO,
     * or with status {@code 400 (Bad Request)} if the certificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the certificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the certificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CertificationDTO> partialUpdateCertification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CertificationDTO certificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Certification partially : {}, {}", id, certificationDTO);
        if (certificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CertificationDTO> result = certificationService.partialUpdate(certificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, certificationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /certifications} : get all the certifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certifications in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CertificationDTO>> getAllCertifications(
        CertificationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Certifications by criteria: {}", criteria);

        Page<CertificationDTO> page = certificationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /certifications/count} : count all the certifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCertifications(CertificationCriteria criteria) {
        log.debug("REST request to count Certifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(certificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /certifications/:id} : get the "id" certification.
     *
     * @param id the id of the certificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificationDTO> getCertification(@PathVariable("id") Long id) {
        log.debug("REST request to get Certification : {}", id);
        Optional<CertificationDTO> certificationDTO = certificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certificationDTO);
    }

    /**
     * {@code DELETE  /certifications/:id} : delete the "id" certification.
     *
     * @param id the id of the certificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable("id") Long id) {
        log.debug("REST request to delete Certification : {}", id);
        certificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
