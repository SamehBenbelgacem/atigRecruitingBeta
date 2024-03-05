package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.repository.EmailRepository;
import atig.recruiting.beta.service.criteria.EmailCriteria;
import atig.recruiting.beta.service.dto.EmailDTO;
import atig.recruiting.beta.service.mapper.EmailMapper;
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
 * Service for executing complex queries for {@link Email} entities in the database.
 * The main input is a {@link EmailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmailDTO} or a {@link Page} of {@link EmailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailQueryService extends QueryService<Email> {

    private final Logger log = LoggerFactory.getLogger(EmailQueryService.class);

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    public EmailQueryService(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    /**
     * Return a {@link List} of {@link EmailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmailDTO> findByCriteria(EmailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Email> specification = createSpecification(criteria);
        return emailMapper.toDto(emailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailDTO> findByCriteria(EmailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Email> specification = createSpecification(criteria);
        return emailRepository.findAll(specification, page).map(emailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Email> specification = createSpecification(criteria);
        return emailRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Email> createSpecification(EmailCriteria criteria) {
        Specification<Email> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Email_.id));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFrom(), Email_.from));
            }
            if (criteria.getRecipients() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecipients(), Email_.recipients));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Email_.subject));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Email_.text));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Email_.type));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Email_.date));
            }
            if (criteria.getSnoozedTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSnoozedTo(), Email_.snoozedTo));
            }
            if (criteria.getFolder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolder(), Email_.folder));
            }
            if (criteria.getSignatureText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignatureText(), Email_.signatureText));
            }
            if (criteria.getJoinedFilesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getJoinedFilesId(),
                            root -> root.join(Email_.joinedFiles, JoinType.LEFT).get(ObjectContainingFile_.id)
                        )
                    );
            }
            if (criteria.getSubEmailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSubEmailsId(), root -> root.join(Email_.subEmails, JoinType.LEFT).get(SubEmail_.id))
                    );
            }
            if (criteria.getEmailEmailcredentialsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmailEmailcredentialsId(),
                            root -> root.join(Email_.emailEmailcredentials, JoinType.LEFT).get(Emailcredentials_.id)
                        )
                    );
            }
            if (criteria.getEmailCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmailCandidateId(),
                            root -> root.join(Email_.emailCandidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
            if (criteria.getEmailCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmailCompanyId(),
                            root -> root.join(Email_.emailCompany, JoinType.LEFT).get(Company_.id)
                        )
                    );
            }
            if (criteria.getCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCandidateId(), root -> root.join(Email_.candidate, JoinType.LEFT).get(Candidate_.id))
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Email_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
            if (criteria.getEmailcredentialsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmailcredentialsId(),
                            root -> root.join(Email_.emailcredentials, JoinType.LEFT).get(Emailcredentials_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
