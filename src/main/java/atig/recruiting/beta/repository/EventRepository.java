package atig.recruiting.beta.repository;

import atig.recruiting.beta.domain.Event;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query("select event from Event event where event.user.login = ?#{authentication.name}")
    List<Event> findByUserIsCurrentUser();
}
