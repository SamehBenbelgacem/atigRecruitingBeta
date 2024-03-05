package atig.recruiting.beta.web.rest;

import atig.recruiting.beta.repository.ObjectContainingFileRepository;
import atig.recruiting.beta.service.ObjectContainingFileQueryService;
import atig.recruiting.beta.service.ObjectContainingFileService;
import atig.recruiting.beta.service.criteria.ObjectContainingFileCriteria;
import atig.recruiting.beta.service.dto.ObjectContainingFileDTO;
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
 * REST controller for managing {@link atig.recruiting.beta.domain.ObjectContainingFile}.
 */
@RestController
@RequestMapping("/api/object-containing-files")
public class ObjectContainingFileResource {

    private final Logger log = LoggerFactory.getLogger(ObjectContainingFileResource.class);

    private static final String ENTITY_NAME = "objectContainingFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObjectContainingFileService objectContainingFileService;

    private final ObjectContainingFileRepository objectContainingFileRepository;

    private final ObjectContainingFileQueryService objectContainingFileQueryService;

    public ObjectContainingFileResource(
        ObjectContainingFileService objectContainingFileService,
        ObjectContainingFileRepository objectContainingFileRepository,
        ObjectContainingFileQueryService objectContainingFileQueryService
    ) {
        this.objectContainingFileService = objectContainingFileService;
        this.objectContainingFileRepository = objectContainingFileRepository;
        this.objectContainingFileQueryService = objectContainingFileQueryService;
    }

    /**
     * {@code POST  /object-containing-files} : Create a new objectContainingFile.
     *
     * @param objectContainingFileDTO the objectContainingFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new objectContainingFileDTO, or with status {@code 400 (Bad Request)} if the objectContainingFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ObjectContainingFileDTO> createObjectContainingFile(@RequestBody ObjectContainingFileDTO objectContainingFileDTO)
        throws URISyntaxException {
        log.debug("REST request to save ObjectContainingFile : {}", objectContainingFileDTO);
        if (objectContainingFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new objectContainingFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObjectContainingFileDTO result = objectContainingFileService.save(objectContainingFileDTO);
        return ResponseEntity
            .created(new URI("/api/object-containing-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /object-containing-files/:id} : Updates an existing objectContainingFile.
     *
     * @param id the id of the objectContainingFileDTO to save.
     * @param objectContainingFileDTO the objectContainingFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objectContainingFileDTO,
     * or with status {@code 400 (Bad Request)} if the objectContainingFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the objectContainingFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ObjectContainingFileDTO> updateObjectContainingFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ObjectContainingFileDTO objectContainingFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ObjectContainingFile : {}, {}", id, objectContainingFileDTO);
        if (objectContainingFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objectContainingFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objectContainingFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObjectContainingFileDTO result = objectContainingFileService.update(objectContainingFileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objectContainingFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /object-containing-files/:id} : Partial updates given fields of an existing objectContainingFile, field will ignore if it is null
     *
     * @param id the id of the objectContainingFileDTO to save.
     * @param objectContainingFileDTO the objectContainingFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objectContainingFileDTO,
     * or with status {@code 400 (Bad Request)} if the objectContainingFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the objectContainingFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the objectContainingFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObjectContainingFileDTO> partialUpdateObjectContainingFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ObjectContainingFileDTO objectContainingFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ObjectContainingFile partially : {}, {}", id, objectContainingFileDTO);
        if (objectContainingFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objectContainingFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objectContainingFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObjectContainingFileDTO> result = objectContainingFileService.partialUpdate(objectContainingFileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objectContainingFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /object-containing-files} : get all the objectContainingFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of objectContainingFiles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ObjectContainingFileDTO>> getAllObjectContainingFiles(
        ObjectContainingFileCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ObjectContainingFiles by criteria: {}", criteria);

        Page<ObjectContainingFileDTO> page = objectContainingFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /object-containing-files/count} : count all the objectContainingFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countObjectContainingFiles(ObjectContainingFileCriteria criteria) {
        log.debug("REST request to count ObjectContainingFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(objectContainingFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /object-containing-files/:id} : get the "id" objectContainingFile.
     *
     * @param id the id of the objectContainingFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the objectContainingFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObjectContainingFileDTO> getObjectContainingFile(@PathVariable("id") Long id) {
        log.debug("REST request to get ObjectContainingFile : {}", id);
        Optional<ObjectContainingFileDTO> objectContainingFileDTO = objectContainingFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(objectContainingFileDTO);
    }

    /**
     * {@code DELETE  /object-containing-files/:id} : delete the "id" objectContainingFile.
     *
     * @param id the id of the objectContainingFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjectContainingFile(@PathVariable("id") Long id) {
        log.debug("REST request to delete ObjectContainingFile : {}", id);
        objectContainingFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
