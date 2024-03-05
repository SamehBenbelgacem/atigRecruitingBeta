package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Note;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.dto.CompanyDTO;
import atig.recruiting.beta.service.dto.NoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {
    @Mapping(target = "noteCompany", source = "noteCompany", qualifiedByName = "companyId")
    @Mapping(target = "noteCandidate", source = "noteCandidate", qualifiedByName = "candidateId")
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "candidateId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    NoteDTO toDto(Note s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);

    @Named("candidateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateDTO toDtoCandidateId(Candidate candidate);
}
