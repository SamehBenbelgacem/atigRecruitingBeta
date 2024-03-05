package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Desider;
import atig.recruiting.beta.repository.DesiderRepository;
import atig.recruiting.beta.service.criteria.DesiderCriteria;
import atig.recruiting.beta.service.dto.DesiderDTO;
import atig.recruiting.beta.service.mapper.DesiderMapper;
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
 * Service for executing complex queries for {@link Desider} entities in the database.
 * The main input is a {@link DesiderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DesiderDTO} or a {@link Page} of {@link DesiderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DesiderQueryService extends QueryService<Desider> {

    private final Logger log = LoggerFactory.getLogger(DesiderQueryService.class);

    private final DesiderRepository desiderRepository;

    private final DesiderMapper desiderMapper;

    public DesiderQueryService(DesiderRepository desiderRepository, DesiderMapper desiderMapper) {
        this.desiderRepository = desiderRepository;
        this.desiderMapper = desiderMapper;
    }

    /**
     * Return a {@link List} of {@link DesiderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DesiderDTO> findByCriteria(DesiderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Desider> specification = createSpecification(criteria);
        return desiderMapper.toDto(desiderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DesiderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DesiderDTO> findByCriteria(DesiderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Desider> specification = createSpecification(criteria);
        return desiderRepository.findAll(specification, page).map(desiderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DesiderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Desider> specification = createSpecification(criteria);
        return desiderRepository.count(specification);
    }

    /**
     * Function to convert {@link DesiderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Desider> createSpecification(DesiderCriteria criteria) {
        Specification<Desider> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Desider_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Desider_.fullName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Desider_.email));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), Desider_.mobile));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), Desider_.role));
            }
            if (criteria.getDesiderCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesiderCompanyId(),
                            root -> root.join(Desider_.desiderCompany, JoinType.LEFT).get(Company_.id)
                        )
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Desider_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
        }
        return specification;
    }
}
