package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Experience;
import atig.recruiting.beta.domain.Tool;
import atig.recruiting.beta.service.dto.ExperienceDTO;
import atig.recruiting.beta.service.dto.ToolDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tool} and its DTO {@link ToolDTO}.
 */
@Mapper(componentModel = "spring")
public interface ToolMapper extends EntityMapper<ToolDTO, Tool> {
    @Mapping(target = "toolExperience", source = "toolExperience", qualifiedByName = "experienceId")
    @Mapping(target = "experience", source = "experience", qualifiedByName = "experienceId")
    ToolDTO toDto(Tool s);

    @Named("experienceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExperienceDTO toDtoExperienceId(Experience experience);
}
