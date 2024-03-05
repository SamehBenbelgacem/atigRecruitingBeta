package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.domain.ProcessStep;
import atig.recruiting.beta.service.dto.ProcessDTO;
import atig.recruiting.beta.service.dto.ProcessStepDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessStep} and its DTO {@link ProcessStepDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcessStepMapper extends EntityMapper<ProcessStepDTO, ProcessStep> {
    @Mapping(target = "processStepProcess", source = "processStepProcess", qualifiedByName = "processId")
    @Mapping(target = "process", source = "process", qualifiedByName = "processId")
    ProcessStepDTO toDto(ProcessStep s);

    @Named("processId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessDTO toDtoProcessId(Process process);
}
