package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.EmailcredentialsRepository;
import atig.recruiting.beta.service.EmailcredentialsQueryService;
import atig.recruiting.beta.service.EmailcredentialsService;
import atig.recruiting.beta.service.criteria.EmailcredentialsCriteria;
import atig.recruiting.beta.service.dto.EmailcredentialsDTO;
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
 * REST controller for managing {@link atig.recruiting.beta.domain.Emailcredentials}.
 */
@RestController
@RequestMapping("/api/emailcredentials")
public class EmailcredentialsResource {

    private final Logger log = LoggerFactory.getLogger(EmailcredentialsResource.class);

    private static final String ENTITY_NAME = "emailcredentials";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailcredentialsService emailcredentialsService;

    private final EmailcredentialsRepository emailcredentialsRepository;

    private final EmailcredentialsQueryService emailcredentialsQueryService;

    public EmailcredentialsResource(
        EmailcredentialsService emailcredentialsService,
        EmailcredentialsRepository emailcredentialsRepository,
        EmailcredentialsQueryService emailcredentialsQueryService
    ) {
        this.emailcredentialsService = emailcredentialsService;
        this.emailcredentialsRepository = emailcredentialsRepository;
        this.emailcredentialsQueryService = emailcredentialsQueryService;
    }

    /**
     * {@code POST  /emailcredentials} : Create a new emailcredentials.
     *
     * @param emailcredentialsDTO the emailcredentialsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailcredentialsDTO, or with status {@code 400 (Bad Request)} if the emailcredentials has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmailcredentialsDTO> createEmailcredentials(@RequestBody EmailcredentialsDTO emailcredentialsDTO)
        throws URISyntaxException {
        log.debug("REST request to save Emailcredentials : {}", emailcredentialsDTO);
        if (emailcredentialsDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailcredentials cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailcredentialsDTO result = emailcredentialsService.save(emailcredentialsDTO);
        return ResponseEntity
            .created(new URI("/api/emailcredentials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emailcredentials/:id} : Updates an existing emailcredentials.
     *
     * @param id the id of the emailcredentialsDTO to save.
     * @param emailcredentialsDTO the emailcredentialsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailcredentialsDTO,
     * or with status {@code 400 (Bad Request)} if the emailcredentialsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailcredentialsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmailcredentialsDTO> updateEmailcredentials(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailcredentialsDTO emailcredentialsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Emailcredentials : {}, {}", id, emailcredentialsDTO);
        if (emailcredentialsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailcredentialsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailcredentialsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmailcredentialsDTO result = emailcredentialsService.update(emailcredentialsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailcredentialsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emailcredentials/:id} : Partial updates given fields of an existing emailcredentials, field will ignore if it is null
     *
     * @param id the id of the emailcredentialsDTO to save.
     * @param emailcredentialsDTO the emailcredentialsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailcredentialsDTO,
     * or with status {@code 400 (Bad Request)} if the emailcredentialsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the emailcredentialsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the emailcredentialsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmailcredentialsDTO> partialUpdateEmailcredentials(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailcredentialsDTO emailcredentialsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Emailcredentials partially : {}, {}", id, emailcredentialsDTO);
        if (emailcredentialsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailcredentialsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailcredentialsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmailcredentialsDTO> result = emailcredentialsService.partialUpdate(emailcredentialsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailcredentialsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /emailcredentials} : get all the emailcredentials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailcredentials in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EmailcredentialsDTO>> getAllEmailcredentials(
        EmailcredentialsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Emailcredentials by criteria: {}", criteria);

        Page<EmailcredentialsDTO> page = emailcredentialsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emailcredentials/count} : count all the emailcredentials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEmailcredentials(EmailcredentialsCriteria criteria) {
        log.debug("REST request to count Emailcredentials by criteria: {}", criteria);
        return ResponseEntity.ok().body(emailcredentialsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emailcredentials/:id} : get the "id" emailcredentials.
     *
     * @param id the id of the emailcredentialsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailcredentialsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmailcredentialsDTO> getEmailcredentials(@PathVariable("id") Long id) {
        log.debug("REST request to get Emailcredentials : {}", id);
        Optional<EmailcredentialsDTO> emailcredentialsDTO = emailcredentialsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailcredentialsDTO);
    }

    /**
     * {@code DELETE  /emailcredentials/:id} : delete the "id" emailcredentials.
     *
     * @param id the id of the emailcredentialsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailcredentials(@PathVariable("id") Long id) {
        log.debug("REST request to delete Emailcredentials : {}", id);
        emailcredentialsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
