package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.Emailcredentials;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.dto.CompanyDTO;
import atig.recruiting.beta.service.dto.EmailDTO;
import atig.recruiting.beta.service.dto.EmailcredentialsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Email} and its DTO {@link EmailDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {
    @Mapping(target = "emailEmailcredentials", source = "emailEmailcredentials", qualifiedByName = "emailcredentialsId")
    @Mapping(target = "emailCandidate", source = "emailCandidate", qualifiedByName = "candidateId")
    @Mapping(target = "emailCompany", source = "emailCompany", qualifiedByName = "companyId")
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "candidateId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    @Mapping(target = "emailcredentials", source = "emailcredentials", qualifiedByName = "emailcredentialsId")
    EmailDTO toDto(Email s);

    @Named("emailcredentialsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmailcredentialsDTO toDtoEmailcredentialsId(Emailcredentials emailcredentials);

    @Named("candidateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateDTO toDtoCandidateId(Candidate candidate);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
