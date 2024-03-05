package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Tool;
import atig.recruiting.beta.repository.ToolRepository;
import atig.recruiting.beta.service.criteria.ToolCriteria;
import atig.recruiting.beta.service.dto.ToolDTO;
import atig.recruiting.beta.service.mapper.ToolMapper;
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
 * Service for executing complex queries for {@link Tool} entities in the database.
 * The main input is a {@link ToolCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ToolDTO} or a {@link Page} of {@link ToolDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ToolQueryService extends QueryService<Tool> {

    private final Logger log = LoggerFactory.getLogger(ToolQueryService.class);

    private final ToolRepository toolRepository;

    private final ToolMapper toolMapper;

    public ToolQueryService(ToolRepository toolRepository, ToolMapper toolMapper) {
        this.toolRepository = toolRepository;
        this.toolMapper = toolMapper;
    }

    /**
     * Return a {@link List} of {@link ToolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ToolDTO> findByCriteria(ToolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tool> specification = createSpecification(criteria);
        return toolMapper.toDto(toolRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ToolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ToolDTO> findByCriteria(ToolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tool> specification = createSpecification(criteria);
        return toolRepository.findAll(specification, page).map(toolMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ToolCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tool> specification = createSpecification(criteria);
        return toolRepository.count(specification);
    }

    /**
     * Function to convert {@link ToolCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tool> createSpecification(ToolCriteria criteria) {
        Specification<Tool> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tool_.id));
            }
            if (criteria.getTool() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTool(), Tool_.tool));
            }
            if (criteria.getToolExperienceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getToolExperienceId(),
                            root -> root.join(Tool_.toolExperience, JoinType.LEFT).get(Experience_.id)
                        )
                    );
            }
            if (criteria.getExperienceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getExperienceId(),
                            root -> root.join(Tool_.experience, JoinType.LEFT).get(Experience_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
