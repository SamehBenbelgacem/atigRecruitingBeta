package atig.recruiting.beta.service;

import atig.recruiting.beta.domain.*; // for static metamodels
import atig.recruiting.beta.domain.Note;
import atig.recruiting.beta.repository.NoteRepository;
import atig.recruiting.beta.service.criteria.NoteCriteria;
import atig.recruiting.beta.service.dto.NoteDTO;
import atig.recruiting.beta.service.mapper.NoteMapper;
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
 * Service for executing complex queries for {@link Note} entities in the database.
 * The main input is a {@link NoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoteDTO} or a {@link Page} of {@link NoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoteQueryService extends QueryService<Note> {

    private final Logger log = LoggerFactory.getLogger(NoteQueryService.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    public NoteQueryService(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    /**
     * Return a {@link List} of {@link NoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoteDTO> findByCriteria(NoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Note> specification = createSpecification(criteria);
        return noteMapper.toDto(noteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoteDTO> findByCriteria(NoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Note> specification = createSpecification(criteria);
        return noteRepository.findAll(specification, page).map(noteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Note> specification = createSpecification(criteria);
        return noteRepository.count(specification);
    }

    /**
     * Function to convert {@link NoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Note> createSpecification(NoteCriteria criteria) {
        Specification<Note> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Note_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Note_.title));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Note_.date));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Note_.description));
            }
            if (criteria.getDocumentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocumentsId(),
                            root -> root.join(Note_.documents, JoinType.LEFT).get(ObjectContainingFile_.id)
                        )
                    );
            }
            if (criteria.getNoteCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNoteCompanyId(),
                            root -> root.join(Note_.noteCompany, JoinType.LEFT).get(Company_.id)
                        )
                    );
            }
            if (criteria.getNoteCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNoteCandidateId(),
                            root -> root.join(Note_.noteCandidate, JoinType.LEFT).get(Candidate_.id)
                        )
                    );
            }
            if (criteria.getCandidateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCandidateId(), root -> root.join(Note_.candidate, JoinType.LEFT).get(Candidate_.id))
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Note_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
        }
        return specification;
    }
}
