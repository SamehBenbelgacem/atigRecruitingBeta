package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.SubEmail;
import atig.recruiting.beta.repository.SubEmailRepository;
import atig.recruiting.beta.service.criteria.SubEmailCriteria;
import atig.recruiting.beta.service.dto.SubEmailDTO;
import atig.recruiting.beta.service.mapper.SubEmailMapper;
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
 * Service for executing complex queries for {@link SubEmail} entities in the database.
 * The main input is a {@link SubEmailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubEmailDTO} or a {@link Page} of {@link SubEmailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubEmailQueryService extends QueryService<SubEmail> {

    private final Logger log = LoggerFactory.getLogger(SubEmailQueryService.class);

    private final SubEmailRepository subEmailRepository;

    private final SubEmailMapper subEmailMapper;

    public SubEmailQueryService(SubEmailRepository subEmailRepository, SubEmailMapper subEmailMapper) {
        this.subEmailRepository = subEmailRepository;
        this.subEmailMapper = subEmailMapper;
    }

    /**
     * Return a {@link List} of {@link SubEmailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubEmailDTO> findByCriteria(SubEmailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubEmail> specification = createSpecification(criteria);
        return subEmailMapper.toDto(subEmailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubEmailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubEmailDTO> findByCriteria(SubEmailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubEmail> specification = createSpecification(criteria);
        return subEmailRepository.findAll(specification, page).map(subEmailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubEmailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubEmail> specification = createSpecification(criteria);
        return subEmailRepository.count(specification);
    }

    /**
     * Function to convert {@link SubEmailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubEmail> createSpecification(SubEmailCriteria criteria) {
        Specification<SubEmail> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubEmail_.id));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFrom(), SubEmail_.from));
            }
            if (criteria.getRecipients() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecipients(), SubEmail_.recipients));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), SubEmail_.text));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), SubEmail_.type));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), SubEmail_.date));
            }
            if (criteria.getSnoozedTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSnoozedTo(), SubEmail_.snoozedTo));
            }
            if (criteria.getSignatureText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignatureText(), SubEmail_.signatureText));
            }
            if (criteria.getSubEmailEmailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubEmailEmailId(),
                            root -> root.join(SubEmail_.subEmailEmail, JoinType.LEFT).get(Email_.id)
                        )
                    );
            }
            if (criteria.getEmailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmailId(), root -> root.join(SubEmail_.email, JoinType.LEFT).get(Email_.id))
                    );
            }
        }
        return specification;
    }
}
