package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.ProcessStepRepository;
import atig.recruiting.beta.service.ProcessStepQueryService;
import atig.recruiting.beta.service.ProcessStepService;
import atig.recruiting.beta.service.criteria.ProcessStepCriteria;
import atig.recruiting.beta.service.dto.ProcessStepDTO;
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
 * REST controller for managing {@link atig.recruiting.beta.domain.ProcessStep}.
 */
@RestController
@RequestMapping("/api/process-steps")
public class ProcessStepResource {

    private final Logger log = LoggerFactory.getLogger(ProcessStepResource.class);

    private static final String ENTITY_NAME = "processStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessStepService processStepService;

    private final ProcessStepRepository processStepRepository;

    private final ProcessStepQueryService processStepQueryService;

    public ProcessStepResource(
        ProcessStepService processStepService,
        ProcessStepRepository processStepRepository,
        ProcessStepQueryService processStepQueryService
    ) {
        this.processStepService = processStepService;
        this.processStepRepository = processStepRepository;
        this.processStepQueryService = processStepQueryService;
    }

    /**
     * {@code POST  /process-steps} : Create a new processStep.
     *
     * @param processStepDTO the processStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processStepDTO, or with status {@code 400 (Bad Request)} if the processStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProcessStepDTO> createProcessStep(@RequestBody ProcessStepDTO processStepDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessStep : {}", processStepDTO);
        if (processStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new processStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessStepDTO result = processStepService.save(processStepDTO);
        return ResponseEntity
            .created(new URI("/api/process-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-steps/:id} : Updates an existing processStep.
     *
     * @param id the id of the processStepDTO to save.
     * @param processStepDTO the processStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processStepDTO,
     * or with status {@code 400 (Bad Request)} if the processStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProcessStepDTO> updateProcessStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessStepDTO processStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessStep : {}, {}", id, processStepDTO);
        if (processStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessStepDTO result = processStepService.update(processStepDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /process-steps/:id} : Partial updates given fields of an existing processStep, field will ignore if it is null
     *
     * @param id the id of the processStepDTO to save.
     * @param processStepDTO the processStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processStepDTO,
     * or with status {@code 400 (Bad Request)} if the processStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcessStepDTO> partialUpdateProcessStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessStepDTO processStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessStep partially : {}, {}", id, processStepDTO);
        if (processStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessStepDTO> result = processStepService.partialUpdate(processStepDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processStepDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /process-steps} : get all the processSteps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processSteps in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProcessStepDTO>> getAllProcessSteps(
        ProcessStepCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProcessSteps by criteria: {}", criteria);

        Page<ProcessStepDTO> page = processStepQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-steps/count} : count all the processSteps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProcessSteps(ProcessStepCriteria criteria) {
        log.debug("REST request to count ProcessSteps by criteria: {}", criteria);
        return ResponseEntity.ok().body(processStepQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /process-steps/:id} : get the "id" processStep.
     *
     * @param id the id of the processStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProcessStepDTO> getProcessStep(@PathVariable("id") Long id) {
        log.debug("REST request to get ProcessStep : {}", id);
        Optional<ProcessStepDTO> processStepDTO = processStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processStepDTO);
    }

    /**
     * {@code DELETE  /process-steps/:id} : delete the "id" processStep.
     *
     * @param id the id of the processStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessStep(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProcessStep : {}", id);
        processStepService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
