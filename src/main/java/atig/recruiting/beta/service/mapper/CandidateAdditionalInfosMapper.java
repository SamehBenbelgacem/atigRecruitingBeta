package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import atig.recruiting.beta.service.dto.CandidateAdditionalInfosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CandidateAdditionalInfos} and its DTO {@link CandidateAdditionalInfosDTO}.
 */
@Mapper(componentModel = "spring")
public interface CandidateAdditionalInfosMapper extends EntityMapper<CandidateAdditionalInfosDTO, CandidateAdditionalInfos> {}
