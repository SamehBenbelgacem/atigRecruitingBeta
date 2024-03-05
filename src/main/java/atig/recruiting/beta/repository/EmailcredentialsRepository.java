package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Emailcredentials;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Emailcredentials entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailcredentialsRepository extends JpaRepository<Emailcredentials, Long>, JpaSpecificationExecutor<Emailcredentials> {}
