package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.DesiderRepository;
import atig.recruiting.beta.service.DesiderQueryService;
import atig.recruiting.beta.service.DesiderService;
import atig.recruiting.beta.service.criteria.DesiderCriteria;
import atig.recruiting.beta.service.dto.DesiderDTO;
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
 * REST controller for managing {@link atig.recruiting.beta.domain.Desider}.
 */
@RestController
@RequestMapping("/api/desiders")
public class DesiderResource {

    private final Logger log = LoggerFactory.getLogger(DesiderResource.class);

    private static final String ENTITY_NAME = "desider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesiderService desiderService;

    private final DesiderRepository desiderRepository;

    private final DesiderQueryService desiderQueryService;

    public DesiderResource(DesiderService desiderService, DesiderRepository desiderRepository, DesiderQueryService desiderQueryService) {
        this.desiderService = desiderService;
        this.desiderRepository = desiderRepository;
        this.desiderQueryService = desiderQueryService;
    }

    /**
     * {@code POST  /desiders} : Create a new desider.
     *
     * @param desiderDTO the desiderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new desiderDTO, or with status {@code 400 (Bad Request)} if the desider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DesiderDTO> createDesider(@RequestBody DesiderDTO desiderDTO) throws URISyntaxException {
        log.debug("REST request to save Desider : {}", desiderDTO);
        if (desiderDTO.getId() != null) {
            throw new BadRequestAlertException("A new desider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesiderDTO result = desiderService.save(desiderDTO);
        return ResponseEntity
            .created(new URI("/api/desiders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /desiders/:id} : Updates an existing desider.
     *
     * @param id the id of the desiderDTO to save.
     * @param desiderDTO the desiderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated desiderDTO,
     * or with status {@code 400 (Bad Request)} if the desiderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the desiderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DesiderDTO> updateDesider(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DesiderDTO desiderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Desider : {}, {}", id, desiderDTO);
        if (desiderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, desiderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!desiderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DesiderDTO result = desiderService.update(desiderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, desiderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /desiders/:id} : Partial updates given fields of an existing desider, field will ignore if it is null
     *
     * @param id the id of the desiderDTO to save.
     * @param desiderDTO the desiderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated desiderDTO,
     * or with status {@code 400 (Bad Request)} if the desiderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the desiderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the desiderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DesiderDTO> partialUpdateDesider(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DesiderDTO desiderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Desider partially : {}, {}", id, desiderDTO);
        if (desiderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, desiderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!desiderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DesiderDTO> result = desiderService.partialUpdate(desiderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, desiderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /desiders} : get all the desiders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of desiders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DesiderDTO>> getAllDesiders(
        DesiderCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Desiders by criteria: {}", criteria);

        Page<DesiderDTO> page = desiderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /desiders/count} : count all the desiders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDesiders(DesiderCriteria criteria) {
        log.debug("REST request to count Desiders by criteria: {}", criteria);
        return ResponseEntity.ok().body(desiderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /desiders/:id} : get the "id" desider.
     *
     * @param id the id of the desiderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the desiderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DesiderDTO> getDesider(@PathVariable("id") Long id) {
        log.debug("REST request to get Desider : {}", id);
        Optional<DesiderDTO> desiderDTO = desiderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(desiderDTO);
    }

    /**
     * {@code DELETE  /desiders/:id} : delete the "id" desider.
     *
     * @param id the id of the desiderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesider(@PathVariable("id") Long id) {
        log.debug("REST request to delete Desider : {}", id);
        desiderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
