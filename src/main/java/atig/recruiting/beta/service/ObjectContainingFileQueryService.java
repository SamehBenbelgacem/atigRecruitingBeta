package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.ObjectContainingFile;
import atig.recruiting.beta.repository.ObjectContainingFileRepository;
import atig.recruiting.beta.service.criteria.ObjectContainingFileCriteria;
import atig.recruiting.beta.service.dto.ObjectContainingFileDTO;
import atig.recruiting.beta.service.mapper.ObjectContainingFileMapper;
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
 * Service for executing complex queries for {@link ObjectContainingFile} entities in the database.
 * The main input is a {@link ObjectContainingFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ObjectContainingFileDTO} or a {@link Page} of {@link ObjectContainingFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ObjectContainingFileQueryService extends QueryService<ObjectContainingFile> {

    private final Logger log = LoggerFactory.getLogger(ObjectContainingFileQueryService.class);

    private final ObjectContainingFileRepository objectContainingFileRepository;

    private final ObjectContainingFileMapper objectContainingFileMapper;

    public ObjectContainingFileQueryService(
        ObjectContainingFileRepository objectContainingFileRepository,
        ObjectContainingFileMapper objectContainingFileMapper
    ) {
        this.objectContainingFileRepository = objectContainingFileRepository;
        this.objectContainingFileMapper = objectContainingFileMapper;
    }

    /**
     * Return a {@link List} of {@link ObjectContainingFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ObjectContainingFileDTO> findByCriteria(ObjectContainingFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ObjectContainingFile> specification = createSpecification(criteria);
        return objectContainingFileMapper.toDto(objectContainingFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ObjectContainingFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ObjectContainingFileDTO> findByCriteria(ObjectContainingFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ObjectContainingFile> specification = createSpecification(criteria);
        return objectContainingFileRepository.findAll(specification, page).map(objectContainingFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ObjectContainingFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ObjectContainingFile> specification = createSpecification(criteria);
        return objectContainingFileRepository.count(specification);
    }

    /**
     * Function to convert {@link ObjectContainingFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ObjectContainingFile> createSpecification(ObjectContainingFileCriteria criteria) {
        Specification<ObjectContainingFile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ObjectContainingFile_.id));
            }
            if (criteria.getCandidateDocsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateDocsId(),
                            root -> root.join(ObjectContainingFile_.candidateDocs, JoinType.LEFT).get(CandidateAdditionalInfos_.id)
                        )
                    );
            }
            if (criteria.getNoteDocsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNoteDocsId(),
                            root -> root.join(ObjectContainingFile_.noteDocs, JoinType.LEFT).get(Note_.id)
                        )
                    );
            }
            if (criteria.getEmailDocsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmailDocsId(),
                            root -> root.join(ObjectContainingFile_.emailDocs, JoinType.LEFT).get(Email_.id)
                        )
                    );
            }
            if (criteria.getCandidateAdditionalInfosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCandidateAdditionalInfosId(),
                            root ->
                                root.join(ObjectContainingFile_.candidateAdditionalInfos, JoinType.LEFT).get(CandidateAdditionalInfos_.id)
                        )
                    );
            }
            if (criteria.getNoteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNoteId(), root -> root.join(ObjectContainingFile_.note, JoinType.LEFT).get(Note_.id))
                    );
            }
            if (criteria.getEmailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmailId(),
                            root -> root.join(ObjectContainingFile_.email, JoinType.LEFT).get(Email_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
