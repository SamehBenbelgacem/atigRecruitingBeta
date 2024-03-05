package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Experience;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.dto.ExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Experience} and its DTO {@link ExperienceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExperienceMapper extends EntityMapper<ExperienceDTO, Experience> {
    @Mapping(target = "experienceCandidate", source = "experienceCandidate", qualifiedByName = "candidateId")
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "candidateId")
    ExperienceDTO toDto(Experience s);

    @Named("candidateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateDTO toDtoCandidateId(Candidate candidate);
}
