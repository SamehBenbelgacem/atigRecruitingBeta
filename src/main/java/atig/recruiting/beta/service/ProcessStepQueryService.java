package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.ProcessStep;
import atig.recruiting.beta.repository.ProcessStepRepository;
import atig.recruiting.beta.service.criteria.ProcessStepCriteria;
import atig.recruiting.beta.service.dto.ProcessStepDTO;
import atig.recruiting.beta.service.mapper.ProcessStepMapper;
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
 * Service for executing complex queries for {@link ProcessStep} entities in the database.
 * The main input is a {@link ProcessStepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessStepDTO} or a {@link Page} of {@link ProcessStepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessStepQueryService extends QueryService<ProcessStep> {

    private final Logger log = LoggerFactory.getLogger(ProcessStepQueryService.class);

    private final ProcessStepRepository processStepRepository;

    private final ProcessStepMapper processStepMapper;

    public ProcessStepQueryService(ProcessStepRepository processStepRepository, ProcessStepMapper processStepMapper) {
        this.processStepRepository = processStepRepository;
        this.processStepMapper = processStepMapper;
    }

    /**
     * Return a {@link List} of {@link ProcessStepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessStepDTO> findByCriteria(ProcessStepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProcessStep> specification = createSpecification(criteria);
        return processStepMapper.toDto(processStepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProcessStepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessStepDTO> findByCriteria(ProcessStepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProcessStep> specification = createSpecification(criteria);
        return processStepRepository.findAll(specification, page).map(processStepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessStepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProcessStep> specification = createSpecification(criteria);
        return processStepRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessStepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProcessStep> createSpecification(ProcessStepCriteria criteria) {
        Specification<ProcessStep> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProcessStep_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), ProcessStep_.title));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrder(), ProcessStep_.order));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), ProcessStep_.priority));
            }
            if (criteria.getCandidatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidatesId(),
                            root -> root.join(ProcessStep_.candidates, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
            if (criteria.getCompaniesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompaniesId(),
                            root -> root.join(ProcessStep_.companies, JoinType.LEFT).get(Company_.id)
                        )
                    );
            }
            if (criteria.getProcessStepProcessId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessStepProcessId(),
                            root -> root.join(ProcessStep_.processStepProcess, JoinType.LEFT).get(Process_.id)
                        )
                    );
            }
            if (criteria.getProcessId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProcessId(), root -> root.join(ProcessStep_.process, JoinType.LEFT).get(Process_.id))
                    );
            }
        }
        return specification;
    }
}
