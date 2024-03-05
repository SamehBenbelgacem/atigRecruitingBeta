package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Education;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.dto.EducationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Education} and its DTO {@link EducationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {
    @Mapping(target = "educationCandidate", source = "educationCandidate", qualifiedByName = "candidateId")
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "candidateId")
    EducationDTO toDto(Education s);

    @Named("candidateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateDTO toDtoCandidateId(Candidate candidate);
}
