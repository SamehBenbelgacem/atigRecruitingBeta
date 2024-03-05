package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Desider;
import atig.recruiting.beta.service.dto.CompanyDTO;
import atig.recruiting.beta.service.dto.DesiderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Desider} and its DTO {@link DesiderDTO}.
 */
@Mapper(componentModel = "spring")
public interface DesiderMapper extends EntityMapper<DesiderDTO, Desider> {
    @Mapping(target = "desiderCompany", source = "desiderCompany", qualifiedByName = "companyId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    DesiderDTO toDto(Desider s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
