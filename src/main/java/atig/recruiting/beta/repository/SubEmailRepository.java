package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.SubEmail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubEmailRepository extends JpaRepository<SubEmail, Long>, JpaSpecificationExecutor<SubEmail> {}
