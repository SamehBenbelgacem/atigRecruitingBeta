package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Education;
import atig.recruiting.beta.repository.EducationRepository;
import atig.recruiting.beta.service.criteria.EducationCriteria;
import atig.recruiting.beta.service.dto.EducationDTO;
import atig.recruiting.beta.service.mapper.EducationMapper;
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
 * Service for executing complex queries for {@link Education} entities in the database.
 * The main input is a {@link EducationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EducationDTO} or a {@link Page} of {@link EducationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EducationQueryService extends QueryService<Education> {

    private final Logger log = LoggerFactory.getLogger(EducationQueryService.class);

    private final EducationRepository educationRepository;

    private final EducationMapper educationMapper;

    public EducationQueryService(EducationRepository educationRepository, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
    }

    /**
     * Return a {@link List} of {@link EducationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EducationDTO> findByCriteria(EducationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Education> specification = createSpecification(criteria);
        return educationMapper.toDto(educationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EducationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationDTO> findByCriteria(EducationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Education> specification = createSpecification(criteria);
        return educationRepository.findAll(specification, page).map(educationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EducationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Education> specification = createSpecification(criteria);
        return educationRepository.count(specification);
    }

    /**
     * Function to convert {@link EducationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Education> createSpecification(EducationCriteria criteria) {
        Specification<Education> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Education_.id));
            }
            if (criteria.getDiploma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiploma(), Education_.diploma));
            }
            if (criteria.getEstablishment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstablishment(), Education_.establishment));
            }
            if (criteria.getMention() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMention(), Education_.mention));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Education_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Education_.endDate));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), Education_.duration));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Education_.location));
            }
            if (criteria.getEducationCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEducationCandidateId(),
                            root -> root.join(Education_.educationCandidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
            if (criteria.getCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateId(),
                            root -> root.join(Education_.candidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
