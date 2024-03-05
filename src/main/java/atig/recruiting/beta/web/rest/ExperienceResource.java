package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.ExperienceRepository;
import atig.recruiting.beta.service.ExperienceQueryService;
import atig.recruiting.beta.service.ExperienceService;
import atig.recruiting.beta.service.criteria.ExperienceCriteria;
import atig.recruiting.beta.service.dto.ExperienceDTO;
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
 * REST controller for managing {@link atig.recruiting.beta.domain.Experience}.
 */
@RestController
@RequestMapping("/api/experiences")
public class ExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceResource.class);

    private static final String ENTITY_NAME = "experience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExperienceService experienceService;

    private final ExperienceRepository experienceRepository;

    private final ExperienceQueryService experienceQueryService;

    public ExperienceResource(
        ExperienceService experienceService,
        ExperienceRepository experienceRepository,
        ExperienceQueryService experienceQueryService
    ) {
        this.experienceService = experienceService;
        this.experienceRepository = experienceRepository;
        this.experienceQueryService = experienceQueryService;
    }

    /**
     * {@code POST  /experiences} : Create a new experience.
     *
     * @param experienceDTO the experienceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new experienceDTO, or with status {@code 400 (Bad Request)} if the experience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ExperienceDTO> createExperience(@RequestBody ExperienceDTO experienceDTO) throws URISyntaxException {
        log.debug("REST request to save Experience : {}", experienceDTO);
        if (experienceDTO.getId() != null) {
            throw new BadRequestAlertException("A new experience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperienceDTO result = experienceService.save(experienceDTO);
        return ResponseEntity
            .created(new URI("/api/experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /experiences/:id} : Updates an existing experience.
     *
     * @param id the id of the experienceDTO to save.
     * @param experienceDTO the experienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experienceDTO,
     * or with status {@code 400 (Bad Request)} if the experienceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the experienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExperienceDTO> updateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExperienceDTO experienceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Experience : {}, {}", id, experienceDTO);
        if (experienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experienceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExperienceDTO result = experienceService.update(experienceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, experienceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /experiences/:id} : Partial updates given fields of an existing experience, field will ignore if it is null
     *
     * @param id the id of the experienceDTO to save.
     * @param experienceDTO the experienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experienceDTO,
     * or with status {@code 400 (Bad Request)} if the experienceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the experienceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the experienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExperienceDTO> partialUpdateExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExperienceDTO experienceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Experience partially : {}, {}", id, experienceDTO);
        if (experienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experienceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExperienceDTO> result = experienceService.partialUpdate(experienceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, experienceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /experiences} : get all the experiences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of experiences in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ExperienceDTO>> getAllExperiences(
        ExperienceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Experiences by criteria: {}", criteria);

        Page<ExperienceDTO> page = experienceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /experiences/count} : count all the experiences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countExperiences(ExperienceCriteria criteria) {
        log.debug("REST request to count Experiences by criteria: {}", criteria);
        return ResponseEntity.ok().body(experienceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /experiences/:id} : get the "id" experience.
     *
     * @param id the id of the experienceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the experienceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDTO> getExperience(@PathVariable("id") Long id) {
        log.debug("REST request to get Experience : {}", id);
        Optional<ExperienceDTO> experienceDTO = experienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(experienceDTO);
    }

    /**
     * {@code DELETE  /experiences/:id} : delete the "id" experience.
     *
     * @param id the id of the experienceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable("id") Long id) {
        log.debug("REST request to delete Experience : {}", id);
        experienceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
