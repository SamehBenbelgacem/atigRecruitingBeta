package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.CandidateAdditionalInfosRepository;
import atig.recruiting.beta.service.CandidateAdditionalInfosQueryService;
import atig.recruiting.beta.service.CandidateAdditionalInfosService;
import atig.recruiting.beta.service.criteria.CandidateAdditionalInfosCriteria;
import atig.recruiting.beta.service.dto.CandidateAdditionalInfosDTO;
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
 * REST controller for managing {@link atig.recruiting.beta.domain.CandidateAdditionalInfos}.
 */
@RestController
@RequestMapping("/api/candidate-additional-infos")
public class CandidateAdditionalInfosResource {

    private final Logger log = LoggerFactory.getLogger(CandidateAdditionalInfosResource.class);

    private static final String ENTITY_NAME = "candidateAdditionalInfos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateAdditionalInfosService candidateAdditionalInfosService;

    private final CandidateAdditionalInfosRepository candidateAdditionalInfosRepository;

    private final CandidateAdditionalInfosQueryService candidateAdditionalInfosQueryService;

    public CandidateAdditionalInfosResource(
        CandidateAdditionalInfosService candidateAdditionalInfosService,
        CandidateAdditionalInfosRepository candidateAdditionalInfosRepository,
        CandidateAdditionalInfosQueryService candidateAdditionalInfosQueryService
    ) {
        this.candidateAdditionalInfosService = candidateAdditionalInfosService;
        this.candidateAdditionalInfosRepository = candidateAdditionalInfosRepository;
        this.candidateAdditionalInfosQueryService = candidateAdditionalInfosQueryService;
    }

    /**
     * {@code POST  /candidate-additional-infos} : Create a new candidateAdditionalInfos.
     *
     * @param candidateAdditionalInfosDTO the candidateAdditionalInfosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateAdditionalInfosDTO, or with status {@code 400 (Bad Request)} if the candidateAdditionalInfos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CandidateAdditionalInfosDTO> createCandidateAdditionalInfos(
        @RequestBody CandidateAdditionalInfosDTO candidateAdditionalInfosDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CandidateAdditionalInfos : {}", candidateAdditionalInfosDTO);
        if (candidateAdditionalInfosDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateAdditionalInfos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateAdditionalInfosDTO result = candidateAdditionalInfosService.save(candidateAdditionalInfosDTO);
        return ResponseEntity
            .created(new URI("/api/candidate-additional-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidate-additional-infos/:id} : Updates an existing candidateAdditionalInfos.
     *
     * @param id the id of the candidateAdditionalInfosDTO to save.
     * @param candidateAdditionalInfosDTO the candidateAdditionalInfosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateAdditionalInfosDTO,
     * or with status {@code 400 (Bad Request)} if the candidateAdditionalInfosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateAdditionalInfosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CandidateAdditionalInfosDTO> updateCandidateAdditionalInfos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidateAdditionalInfosDTO candidateAdditionalInfosDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CandidateAdditionalInfos : {}, {}", id, candidateAdditionalInfosDTO);
        if (candidateAdditionalInfosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidateAdditionalInfosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidateAdditionalInfosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CandidateAdditionalInfosDTO result = candidateAdditionalInfosService.update(candidateAdditionalInfosDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, candidateAdditionalInfosDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /candidate-additional-infos/:id} : Partial updates given fields of an existing candidateAdditionalInfos, field will ignore if it is null
     *
     * @param id the id of the candidateAdditionalInfosDTO to save.
     * @param candidateAdditionalInfosDTO the candidateAdditionalInfosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateAdditionalInfosDTO,
     * or with status {@code 400 (Bad Request)} if the candidateAdditionalInfosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the candidateAdditionalInfosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidateAdditionalInfosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CandidateAdditionalInfosDTO> partialUpdateCandidateAdditionalInfos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidateAdditionalInfosDTO candidateAdditionalInfosDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CandidateAdditionalInfos partially : {}, {}", id, candidateAdditionalInfosDTO);
        if (candidateAdditionalInfosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidateAdditionalInfosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidateAdditionalInfosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CandidateAdditionalInfosDTO> result = candidateAdditionalInfosService.partialUpdate(candidateAdditionalInfosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, candidateAdditionalInfosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /candidate-additional-infos} : get all the candidateAdditionalInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidateAdditionalInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CandidateAdditionalInfosDTO>> getAllCandidateAdditionalInfos(
        CandidateAdditionalInfosCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CandidateAdditionalInfos by criteria: {}", criteria);

        Page<CandidateAdditionalInfosDTO> page = candidateAdditionalInfosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidate-additional-infos/count} : count all the candidateAdditionalInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCandidateAdditionalInfos(CandidateAdditionalInfosCriteria criteria) {
        log.debug("REST request to count CandidateAdditionalInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(candidateAdditionalInfosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /candidate-additional-infos/:id} : get the "id" candidateAdditionalInfos.
     *
     * @param id the id of the candidateAdditionalInfosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateAdditionalInfosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidateAdditionalInfosDTO> getCandidateAdditionalInfos(@PathVariable("id") Long id) {
        log.debug("REST request to get CandidateAdditionalInfos : {}", id);
        Optional<CandidateAdditionalInfosDTO> candidateAdditionalInfosDTO = candidateAdditionalInfosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidateAdditionalInfosDTO);
    }

    /**
     * {@code DELETE  /candidate-additional-infos/:id} : delete the "id" candidateAdditionalInfos.
     *
     * @param id the id of the candidateAdditionalInfosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidateAdditionalInfos(@PathVariable("id") Long id) {
        log.debug("REST request to delete CandidateAdditionalInfos : {}", id);
        candidateAdditionalInfosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
