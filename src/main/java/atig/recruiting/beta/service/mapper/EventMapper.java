package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Event;
import atig.recruiting.beta.domain.User;
import atig.recruiting.beta.service.dto.EventDTO;
import atig.recruiting.beta.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    EventDTO toDto(Event s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
