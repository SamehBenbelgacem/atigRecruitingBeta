package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Offer;
import atig.recruiting.beta.repository.OfferRepository;
import atig.recruiting.beta.service.criteria.OfferCriteria;
import atig.recruiting.beta.service.dto.OfferDTO;
import atig.recruiting.beta.service.mapper.OfferMapper;
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
 * Service for executing complex queries for {@link Offer} entities in the database.
 * The main input is a {@link OfferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OfferDTO} or a {@link Page} of {@link OfferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfferQueryService extends QueryService<Offer> {

    private final Logger log = LoggerFactory.getLogger(OfferQueryService.class);

    private final OfferRepository offerRepository;

    private final OfferMapper offerMapper;

    public OfferQueryService(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    /**
     * Return a {@link List} of {@link OfferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OfferDTO> findByCriteria(OfferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Offer> specification = createSpecification(criteria);
        return offerMapper.toDto(offerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OfferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OfferDTO> findByCriteria(OfferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Offer> specification = createSpecification(criteria);
        return offerRepository.findAll(specification, page).map(offerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Offer> specification = createSpecification(criteria);
        return offerRepository.count(specification);
    }

    /**
     * Function to convert {@link OfferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Offer> createSpecification(OfferCriteria criteria) {
        Specification<Offer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Offer_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Offer_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Offer_.description));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Offer_.date));
            }
            if (criteria.getOfferCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOfferCompanyId(),
                            root -> root.join(Offer_.offerCompany, JoinType.LEFT).get(Company_.id)
                        )
                    );
            }
            if (criteria.getTagsId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getTagsId(), root -> root.join(Offer_.tags, JoinType.LEFT).get(Tag_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Offer_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
        }
        return specification;
    }
}
