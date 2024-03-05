package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.service.dto.ProcessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Process} and its DTO {@link ProcessDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcessMapper extends EntityMapper<ProcessDTO, Process> {}
