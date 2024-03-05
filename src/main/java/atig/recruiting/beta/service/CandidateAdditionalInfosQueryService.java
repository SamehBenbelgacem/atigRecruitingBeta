package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import atig.recruiting.beta.repository.CandidateAdditionalInfosRepository;
import atig.recruiting.beta.service.criteria.CandidateAdditionalInfosCriteria;
import atig.recruiting.beta.service.dto.CandidateAdditionalInfosDTO;
import atig.recruiting.beta.service.mapper.CandidateAdditionalInfosMapper;
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
 * Service for executing complex queries for {@link CandidateAdditionalInfos} entities in the database.
 * The main input is a {@link CandidateAdditionalInfosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CandidateAdditionalInfosDTO} or a {@link Page} of {@link CandidateAdditionalInfosDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidateAdditionalInfosQueryService extends QueryService<CandidateAdditionalInfos> {

    private final Logger log = LoggerFactory.getLogger(CandidateAdditionalInfosQueryService.class);

    private final CandidateAdditionalInfosRepository candidateAdditionalInfosRepository;

    private final CandidateAdditionalInfosMapper candidateAdditionalInfosMapper;

    public CandidateAdditionalInfosQueryService(
        CandidateAdditionalInfosRepository candidateAdditionalInfosRepository,
        CandidateAdditionalInfosMapper candidateAdditionalInfosMapper
    ) {
        this.candidateAdditionalInfosRepository = candidateAdditionalInfosRepository;
        this.candidateAdditionalInfosMapper = candidateAdditionalInfosMapper;
    }

    /**
     * Return a {@link List} of {@link CandidateAdditionalInfosDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CandidateAdditionalInfosDTO> findByCriteria(CandidateAdditionalInfosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CandidateAdditionalInfos> specification = createSpecification(criteria);
        return candidateAdditionalInfosMapper.toDto(candidateAdditionalInfosRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CandidateAdditionalInfosDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CandidateAdditionalInfosDTO> findByCriteria(CandidateAdditionalInfosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CandidateAdditionalInfos> specification = createSpecification(criteria);
        return candidateAdditionalInfosRepository.findAll(specification, page).map(candidateAdditionalInfosMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CandidateAdditionalInfosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CandidateAdditionalInfos> specification = createSpecification(criteria);
        return candidateAdditionalInfosRepository.count(specification);
    }

    /**
     * Function to convert {@link CandidateAdditionalInfosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CandidateAdditionalInfos> createSpecification(CandidateAdditionalInfosCriteria criteria) {
        Specification<CandidateAdditionalInfos> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CandidateAdditionalInfos_.id));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), CandidateAdditionalInfos_.birthday));
            }
            if (criteria.getActualSalary() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getActualSalary(), CandidateAdditionalInfos_.actualSalary));
            }
            if (criteria.getExpectedSalary() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExpectedSalary(), CandidateAdditionalInfos_.expectedSalary));
            }
            if (criteria.getFirstContact() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFirstContact(), CandidateAdditionalInfos_.firstContact));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), CandidateAdditionalInfos_.location));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), CandidateAdditionalInfos_.mobile));
            }
            if (criteria.getDisponibility() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDisponibility(), CandidateAdditionalInfos_.disponibility));
            }
            if (criteria.getDocumentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocumentsId(),
                            root -> root.join(CandidateAdditionalInfos_.documents, JoinType.LEFT).get(ObjectContainingFile_.id)
                        )
                    );
            }
            if (criteria.getCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateId(),
                            root -> root.join(CandidateAdditionalInfos_.candidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
