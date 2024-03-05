package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.repository.CompanyRepository;
import atig.recruiting.beta.service.criteria.CompanyCriteria;
import atig.recruiting.beta.service.dto.CompanyDTO;
import atig.recruiting.beta.service.mapper.CompanyMapper;
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
 * Service for executing complex queries for {@link Company} entities in the database.
 * The main input is a {@link CompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyDTO} or a {@link Page} of {@link CompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

    private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyQueryService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByCriteria(CompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyMapper.toDto(companyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findByCriteria(CompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.findAll(specification, page).map(companyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Company> createSpecification(CompanyCriteria criteria) {
        Specification<Company> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Company_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Company_.name));
            }
            if (criteria.getSpeciality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpeciality(), Company_.speciality));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Company_.description));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), Company_.website));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Company_.location));
            }
            if (criteria.getInfoEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoEmail(), Company_.infoEmail));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Company_.phone));
            }
            if (criteria.getFirstContactDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstContactDate(), Company_.firstContactDate));
            }
            if (criteria.getNotificationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationsId(),
                            root -> root.join(Company_.notifications, JoinType.LEFT).get(Notification_.id)
                        )
                    );
            }
            if (criteria.getNotesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNotesId(), root -> root.join(Company_.notes, JoinType.LEFT).get(Note_.id))
                    );
            }
            if (criteria.getDesidersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDesidersId(), root -> root.join(Company_.desiders, JoinType.LEFT).get(Desider_.id))
                    );
            }
            if (criteria.getOffersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOffersId(), root -> root.join(Company_.offers, JoinType.LEFT).get(Offer_.id))
                    );
            }
            if (criteria.getEmailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmailsId(), root -> root.join(Company_.emails, JoinType.LEFT).get(Email_.id))
                    );
            }
            if (criteria.getCompanyCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanyCategoryId(),
                            root -> root.join(Company_.companyCategory, JoinType.LEFT).get(Category_.id)
                        )
                    );
            }
            if (criteria.getCompanySubCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanySubCategoryId(),
                            root -> root.join(Company_.companySubCategory, JoinType.LEFT).get(SubCategory_.id)
                        )
                    );
            }
            if (criteria.getCompanyProcessId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanyProcessId(),
                            root -> root.join(Company_.companyProcess, JoinType.LEFT).get(Process_.id)
                        )
                    );
            }
            if (criteria.getCompanyProcessStepId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanyProcessStepId(),
                            root -> root.join(Company_.companyProcessStep, JoinType.LEFT).get(ProcessStep_.id)
                        )
                    );
            }
            if (criteria.getTagsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTagsId(), root -> root.join(Company_.tags, JoinType.LEFT).get(Tag_.id))
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCategoryId(), root -> root.join(Company_.category, JoinType.LEFT).get(Category_.id))
                    );
            }
            if (criteria.getSubCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubCategoryId(),
                            root -> root.join(Company_.subCategory, JoinType.LEFT).get(SubCategory_.id)
                        )
                    );
            }
            if (criteria.getProcessId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProcessId(), root -> root.join(Company_.process, JoinType.LEFT).get(Process_.id))
                    );
            }
            if (criteria.getProcessStepId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessStepId(),
                            root -> root.join(Company_.processStep, JoinType.LEFT).get(ProcessStep_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
