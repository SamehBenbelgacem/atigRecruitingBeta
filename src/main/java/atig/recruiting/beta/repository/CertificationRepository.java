package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Certification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Certification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long>, JpaSpecificationExecutor<Certification> {}
