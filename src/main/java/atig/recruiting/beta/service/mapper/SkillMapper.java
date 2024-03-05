package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Skill;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.dto.SkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring")
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {
    @Mapping(target = "skillCandidate", source = "skillCandidate", qualifiedByName = "candidateId")
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "candidateId")
    SkillDTO toDto(Skill s);

    @Named("candidateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateDTO toDtoCandidateId(Candidate candidate);
}
