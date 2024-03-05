package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.repository.CandidateRepository;
import atig.recruiting.beta.service.criteria.CandidateCriteria;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.mapper.CandidateMapper;
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
 * Service for executing complex queries for {@link Candidate} entities in the database.
 * The main input is a {@link CandidateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CandidateDTO} or a {@link Page} of {@link CandidateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidateQueryService extends QueryService<Candidate> {

    private final Logger log = LoggerFactory.getLogger(CandidateQueryService.class);

    private final CandidateRepository candidateRepository;

    private final CandidateMapper candidateMapper;

    public CandidateQueryService(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    /**
     * Return a {@link List} of {@link CandidateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CandidateDTO> findByCriteria(CandidateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Candidate> specification = createSpecification(criteria);
        return candidateMapper.toDto(candidateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CandidateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CandidateDTO> findByCriteria(CandidateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Candidate> specification = createSpecification(criteria);
        return candidateRepository.findAll(specification, page).map(candidateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CandidateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Candidate> specification = createSpecification(criteria);
        return candidateRepository.count(specification);
    }

    /**
     * Function to convert {@link CandidateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Candidate> createSpecification(CandidateCriteria criteria) {
        Specification<Candidate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Candidate_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Candidate_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Candidate_.lastName));
            }
            if (criteria.getProfession() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfession(), Candidate_.profession));
            }
            if (criteria.getNbExperience() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbExperience(), Candidate_.nbExperience));
            }
            if (criteria.getPersonalEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonalEmail(), Candidate_.personalEmail));
            }
            if (criteria.getAdditionalInfosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAdditionalInfosId(),
                            root -> root.join(Candidate_.additionalInfos, JoinType.LEFT).get(CandidateAdditionalInfos_.id)
                        )
                    );
            }
            if (criteria.getExperiencesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getExperiencesId(),
                            root -> root.join(Candidate_.experiences, JoinType.LEFT).get(Experience_.id)
                        )
                    );
            }
            if (criteria.getEducationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEducationsId(),
                            root -> root.join(Candidate_.educations, JoinType.LEFT).get(Education_.id)
                        )
                    );
            }
            if (criteria.getCertificationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCertificationsId(),
                            root -> root.join(Candidate_.certifications, JoinType.LEFT).get(Certification_.id)
                        )
                    );
            }
            if (criteria.getSkillsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSkillsId(), root -> root.join(Candidate_.skills, JoinType.LEFT).get(Skill_.id))
                    );
            }
            if (criteria.getLanguagesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLanguagesId(),
                            root -> root.join(Candidate_.languages, JoinType.LEFT).get(Language_.id)
                        )
                    );
            }
            if (criteria.getNotificationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationsId(),
                            root -> root.join(Candidate_.notifications, JoinType.LEFT).get(Notification_.id)
                        )
                    );
            }
            if (criteria.getNotesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNotesId(), root -> root.join(Candidate_.notes, JoinType.LEFT).get(Note_.id))
                    );
            }
            if (criteria.getEmailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmailsId(), root -> root.join(Candidate_.emails, JoinType.LEFT).get(Email_.id))
                    );
            }
            if (criteria.getCandidateCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateCategoryId(),
                            root -> root.join(Candidate_.candidateCategory, JoinType.LEFT).get(Category_.id)
                        )
                    );
            }
            if (criteria.getCandidateSubCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateSubCategoryId(),
                            root -> root.join(Candidate_.candidateSubCategory, JoinType.LEFT).get(SubCategory_.id)
                        )
                    );
            }
            if (criteria.getCandidateProcessId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateProcessId(),
                            root -> root.join(Candidate_.candidateProcess, JoinType.LEFT).get(Process_.id)
                        )
                    );
            }
            if (criteria.getCandidateProcessStepId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateProcessStepId(),
                            root -> root.join(Candidate_.candidateProcessStep, JoinType.LEFT).get(ProcessStep_.id)
                        )
                    );
            }
            if (criteria.getTagsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTagsId(), root -> root.join(Candidate_.tags, JoinType.LEFT).get(Tag_.id))
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(Candidate_.category, JoinType.LEFT).get(Category_.id)
                        )
                    );
            }
            if (criteria.getSubCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubCategoryId(),
                            root -> root.join(Candidate_.subCategory, JoinType.LEFT).get(SubCategory_.id)
                        )
                    );
            }
            if (criteria.getProcessId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProcessId(), root -> root.join(Candidate_.process, JoinType.LEFT).get(Process_.id))
                    );
            }
            if (criteria.getProcessStepId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessStepId(),
                            root -> root.join(Candidate_.processStep, JoinType.LEFT).get(ProcessStep_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
