package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.ObjectContainingFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ObjectContainingFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjectContainingFileRepository
    extends JpaRepository<ObjectContainingFile, Long>, JpaSpecificationExecutor<ObjectContainingFile> {}
