package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.SubEmailRepository;
import atig.recruiting.beta.service.SubEmailQueryService;
import atig.recruiting.beta.service.SubEmailService;
import atig.recruiting.beta.service.criteria.SubEmailCriteria;
import atig.recruiting.beta.service.dto.SubEmailDTO;
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
 * REST controller for managing {@link atig.recruiting.beta.domain.SubEmail}.
 */
@RestController
@RequestMapping("/api/sub-emails")
public class SubEmailResource {

    private final Logger log = LoggerFactory.getLogger(SubEmailResource.class);

    private static final String ENTITY_NAME = "subEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubEmailService subEmailService;

    private final SubEmailRepository subEmailRepository;

    private final SubEmailQueryService subEmailQueryService;

    public SubEmailResource(
        SubEmailService subEmailService,
        SubEmailRepository subEmailRepository,
        SubEmailQueryService subEmailQueryService
    ) {
        this.subEmailService = subEmailService;
        this.subEmailRepository = subEmailRepository;
        this.subEmailQueryService = subEmailQueryService;
    }

    /**
     * {@code POST  /sub-emails} : Create a new subEmail.
     *
     * @param subEmailDTO the subEmailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subEmailDTO, or with status {@code 400 (Bad Request)} if the subEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubEmailDTO> createSubEmail(@RequestBody SubEmailDTO subEmailDTO) throws URISyntaxException {
        log.debug("REST request to save SubEmail : {}", subEmailDTO);
        if (subEmailDTO.getId() != null) {
            throw new BadRequestAlertException("A new subEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubEmailDTO result = subEmailService.save(subEmailDTO);
        return ResponseEntity
            .created(new URI("/api/sub-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-emails/:id} : Updates an existing subEmail.
     *
     * @param id the id of the subEmailDTO to save.
     * @param subEmailDTO the subEmailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subEmailDTO,
     * or with status {@code 400 (Bad Request)} if the subEmailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subEmailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubEmailDTO> updateSubEmail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubEmailDTO subEmailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubEmail : {}, {}", id, subEmailDTO);
        if (subEmailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subEmailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subEmailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubEmailDTO result = subEmailService.update(subEmailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subEmailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-emails/:id} : Partial updates given fields of an existing subEmail, field will ignore if it is null
     *
     * @param id the id of the subEmailDTO to save.
     * @param subEmailDTO the subEmailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subEmailDTO,
     * or with status {@code 400 (Bad Request)} if the subEmailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subEmailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subEmailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubEmailDTO> partialUpdateSubEmail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubEmailDTO subEmailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubEmail partially : {}, {}", id, subEmailDTO);
        if (subEmailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subEmailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subEmailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubEmailDTO> result = subEmailService.partialUpdate(subEmailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subEmailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-emails} : get all the subEmails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subEmails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SubEmailDTO>> getAllSubEmails(
        SubEmailCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SubEmails by criteria: {}", criteria);

        Page<SubEmailDTO> page = subEmailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-emails/count} : count all the subEmails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSubEmails(SubEmailCriteria criteria) {
        log.debug("REST request to count SubEmails by criteria: {}", criteria);
        return ResponseEntity.ok().body(subEmailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sub-emails/:id} : get the "id" subEmail.
     *
     * @param id the id of the subEmailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subEmailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubEmailDTO> getSubEmail(@PathVariable("id") Long id) {
        log.debug("REST request to get SubEmail : {}", id);
        Optional<SubEmailDTO> subEmailDTO = subEmailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subEmailDTO);
    }

    /**
     * {@code DELETE  /sub-emails/:id} : delete the "id" subEmail.
     *
     * @param id the id of the subEmailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubEmail(@PathVariable("id") Long id) {
        log.debug("REST request to delete SubEmail : {}", id);
        subEmailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
