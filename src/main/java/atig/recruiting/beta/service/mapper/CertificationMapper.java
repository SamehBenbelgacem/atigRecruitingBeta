package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Certification;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.dto.CertificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certification} and its DTO {@link CertificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface CertificationMapper extends EntityMapper<CertificationDTO, Certification> {
    @Mapping(target = "certificationCandidate", source = "certificationCandidate", qualifiedByName = "candidateId")
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "candidateId")
    CertificationDTO toDto(Certification s);

    @Named("candidateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateDTO toDtoCandidateId(Candidate candidate);
}
