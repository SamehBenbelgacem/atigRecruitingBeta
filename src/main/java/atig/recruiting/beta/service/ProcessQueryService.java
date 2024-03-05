package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.repository.ProcessRepository;
import atig.recruiting.beta.service.criteria.ProcessCriteria;
import atig.recruiting.beta.service.dto.ProcessDTO;
import atig.recruiting.beta.service.mapper.ProcessMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Process} entities in the database.
 * The main input is a {@link ProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessDTO} or a {@link Page} of {@link ProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessQueryService extends QueryService<Process> {

    private final Logger log = LoggerFactory.getLogger(ProcessQueryService.class);

    private final ProcessRepository processRepository;

    private final ProcessMapper processMapper;

    public ProcessQueryService(ProcessRepository processRepository, ProcessMapper processMapper) {
        this.processRepository = processRepository;
        this.processMapper = processMapper;
    }

    /**
     * Return a {@link List} of {@link ProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessDTO> findByCriteria(ProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Process> specification = createSpecification(criteria);
        return processMapper.toDto(processRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessDTO> findByCriteria(ProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Process> specification = createSpecification(criteria);
        return processRepository.findAll(specification, page).map(processMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Process> specification = createSpecification(criteria);
        return processRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Process> createSpecification(ProcessCriteria criteria) {
        Specification<Process> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Process_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Process_.title));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Process_.type));
            }
            if (criteria.getStepsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStepsId(), root -> root.join(Process_.steps, JoinType.LEFT).get(ProcessStep_.id))
                    );
            }
            if (criteria.getCandidatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidatesId(),
                            root -> root.join(Process_.candidates, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
            if (criteria.getCompaniesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompaniesId(), root -> root.join(Process_.companies, JoinType.LEFT).get(Company_.id))
                    );
            }
        }
        return specification;
    }
}
