package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Emailcredentials;
import atig.recruiting.beta.service.dto.EmailcredentialsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Emailcredentials} and its DTO {@link EmailcredentialsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmailcredentialsMapper extends EntityMapper<EmailcredentialsDTO, Emailcredentials> {}
