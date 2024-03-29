package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Process;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Process entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessRepository extends JpaRepository<Process, Long>, JpaSpecificationExecutor<Process> {}
