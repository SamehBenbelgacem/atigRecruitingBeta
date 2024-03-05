package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Emailcredentials;
import atig.recruiting.beta.repository.EmailcredentialsRepository;
import atig.recruiting.beta.service.criteria.EmailcredentialsCriteria;
import atig.recruiting.beta.service.dto.EmailcredentialsDTO;
import atig.recruiting.beta.service.mapper.EmailcredentialsMapper;
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
 * Service for executing complex queries for {@link Emailcredentials} entities in the database.
 * The main input is a {@link EmailcredentialsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmailcredentialsDTO} or a {@link Page} of {@link EmailcredentialsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailcredentialsQueryService extends QueryService<Emailcredentials> {

    private final Logger log = LoggerFactory.getLogger(EmailcredentialsQueryService.class);

    private final EmailcredentialsRepository emailcredentialsRepository;

    private final EmailcredentialsMapper emailcredentialsMapper;

    public EmailcredentialsQueryService(
        EmailcredentialsRepository emailcredentialsRepository,
        EmailcredentialsMapper emailcredentialsMapper
    ) {
        this.emailcredentialsRepository = emailcredentialsRepository;
        this.emailcredentialsMapper = emailcredentialsMapper;
    }

    /**
     * Return a {@link List} of {@link EmailcredentialsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmailcredentialsDTO> findByCriteria(EmailcredentialsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Emailcredentials> specification = createSpecification(criteria);
        return emailcredentialsMapper.toDto(emailcredentialsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmailcredentialsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailcredentialsDTO> findByCriteria(EmailcredentialsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Emailcredentials> specification = createSpecification(criteria);
        return emailcredentialsRepository.findAll(specification, page).map(emailcredentialsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailcredentialsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Emailcredentials> specification = createSpecification(criteria);
        return emailcredentialsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailcredentialsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Emailcredentials> createSpecification(EmailcredentialsCriteria criteria) {
        Specification<Emailcredentials> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Emailcredentials_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), Emailcredentials_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Emailcredentials_.password));
            }
            if (criteria.getEmailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmailsId(),
                            root -> root.join(Emailcredentials_.emails, JoinType.LEFT).get(Email_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
