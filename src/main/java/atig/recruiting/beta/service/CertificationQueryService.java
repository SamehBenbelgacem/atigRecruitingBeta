package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Certification;
import atig.recruiting.beta.repository.CertificationRepository;
import atig.recruiting.beta.service.criteria.CertificationCriteria;
import atig.recruiting.beta.service.dto.CertificationDTO;
import atig.recruiting.beta.service.mapper.CertificationMapper;
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
 * Service for executing complex queries for {@link Certification} entities in the database.
 * The main input is a {@link CertificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CertificationDTO} or a {@link Page} of {@link CertificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CertificationQueryService extends QueryService<Certification> {

    private final Logger log = LoggerFactory.getLogger(CertificationQueryService.class);

    private final CertificationRepository certificationRepository;

    private final CertificationMapper certificationMapper;

    public CertificationQueryService(CertificationRepository certificationRepository, CertificationMapper certificationMapper) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
    }

    /**
     * Return a {@link List} of {@link CertificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CertificationDTO> findByCriteria(CertificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Certification> specification = createSpecification(criteria);
        return certificationMapper.toDto(certificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CertificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CertificationDTO> findByCriteria(CertificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Certification> specification = createSpecification(criteria);
        return certificationRepository.findAll(specification, page).map(certificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CertificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Certification> specification = createSpecification(criteria);
        return certificationRepository.count(specification);
    }

    /**
     * Function to convert {@link CertificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Certification> createSpecification(CertificationCriteria criteria) {
        Specification<Certification> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Certification_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Certification_.title));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Certification_.date));
            }
            if (criteria.getCertificationCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCertificationCandidateId(),
                            root -> root.join(Certification_.certificationCandidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
            if (criteria.getCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateId(),
                            root -> root.join(Certification_.candidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
