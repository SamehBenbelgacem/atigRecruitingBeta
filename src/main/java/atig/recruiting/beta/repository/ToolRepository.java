package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Tool;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tool entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToolRepository extends JpaRepository<Tool, Long>, JpaSpecificationExecutor<Tool> {}
