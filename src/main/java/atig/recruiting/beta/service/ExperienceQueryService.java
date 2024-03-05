package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Experience;
import atig.recruiting.beta.repository.ExperienceRepository;
import atig.recruiting.beta.service.criteria.ExperienceCriteria;
import atig.recruiting.beta.service.dto.ExperienceDTO;
import atig.recruiting.beta.service.mapper.ExperienceMapper;
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
 * Service for executing complex queries for {@link Experience} entities in the database.
 * The main input is a {@link ExperienceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExperienceDTO} or a {@link Page} of {@link ExperienceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExperienceQueryService extends QueryService<Experience> {

    private final Logger log = LoggerFactory.getLogger(ExperienceQueryService.class);

    private final ExperienceRepository experienceRepository;

    private final ExperienceMapper experienceMapper;

    public ExperienceQueryService(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    /**
     * Return a {@link List} of {@link ExperienceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExperienceDTO> findByCriteria(ExperienceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Experience> specification = createSpecification(criteria);
        return experienceMapper.toDto(experienceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExperienceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExperienceDTO> findByCriteria(ExperienceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Experience> specification = createSpecification(criteria);
        return experienceRepository.findAll(specification, page).map(experienceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExperienceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Experience> specification = createSpecification(criteria);
        return experienceRepository.count(specification);
    }

    /**
     * Function to convert {@link ExperienceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Experience> createSpecification(ExperienceCriteria criteria) {
        Specification<Experience> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Experience_.id));
            }
            if (criteria.getCompany() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompany(), Experience_.company));
            }
            if (criteria.getCompanySite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanySite(), Experience_.companySite));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), Experience_.role));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Experience_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Experience_.endDate));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), Experience_.duration));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Experience_.location));
            }
            if (criteria.getTasks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTasks(), Experience_.tasks));
            }
            if (criteria.getToolsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getToolsId(), root -> root.join(Experience_.tools, JoinType.LEFT).get(Tool_.id))
                    );
            }
            if (criteria.getExperienceCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getExperienceCandidateId(),
                            root -> root.join(Experience_.experienceCandidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
            if (criteria.getCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateId(),
                            root -> root.join(Experience_.candidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
