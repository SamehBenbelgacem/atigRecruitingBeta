package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.SubEmail;
import atig.recruiting.beta.service.dto.EmailDTO;
import atig.recruiting.beta.service.dto.SubEmailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubEmail} and its DTO {@link SubEmailDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubEmailMapper extends EntityMapper<SubEmailDTO, SubEmail> {
    @Mapping(target = "subEmailEmail", source = "subEmailEmail", qualifiedByName = "emailId")
    @Mapping(target = "email", source = "email", qualifiedByName = "emailId")
    SubEmailDTO toDto(SubEmail s);

    @Named("emailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmailDTO toDtoEmailId(Email email);
}
