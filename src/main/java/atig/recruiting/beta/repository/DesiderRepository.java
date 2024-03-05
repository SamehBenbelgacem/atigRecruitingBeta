package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Desider;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Desider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesiderRepository extends JpaRepository<Desider, Long>, JpaSpecificationExecutor<Desider> {}
