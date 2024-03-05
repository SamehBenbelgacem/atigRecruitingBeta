package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CandidateAdditionalInfos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateAdditionalInfosRepository
    extends JpaRepository<CandidateAdditionalInfos, Long>, JpaSpecificationExecutor<CandidateAdditionalInfos> {}
