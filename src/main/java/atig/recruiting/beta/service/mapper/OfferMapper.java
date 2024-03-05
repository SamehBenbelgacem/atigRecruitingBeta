package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Offer;
import atig.recruiting.beta.domain.Tag;
import atig.recruiting.beta.service.dto.CompanyDTO;
import atig.recruiting.beta.service.dto.OfferDTO;
import atig.recruiting.beta.service.dto.TagDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {
    @Mapping(target = "offerCompany", source = "offerCompany", qualifiedByName = "companyId")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagIdSet")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    OfferDTO toDto(Offer s);

    @Mapping(target = "removeTags", ignore = true)
    Offer toEntity(OfferDTO offerDTO);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);

    @Named("tagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TagDTO toDtoTagId(Tag tag);

    @Named("tagIdSet")
    default Set<TagDTO> toDtoTagIdSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagId).collect(Collectors.toSet());
    }
}
