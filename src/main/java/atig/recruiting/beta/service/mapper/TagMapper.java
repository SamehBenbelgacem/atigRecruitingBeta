package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Tag;
import atig.recruiting.beta.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {}
