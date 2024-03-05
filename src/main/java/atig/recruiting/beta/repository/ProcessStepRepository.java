package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.ProcessStep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProcessStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessStepRepository extends JpaRepository<ProcessStep, Long>, JpaSpecificationExecutor<ProcessStep> {}
