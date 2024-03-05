package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Email;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Email entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailRepository extends JpaRepository<Email, Long>, JpaSpecificationExecutor<Email> {}
