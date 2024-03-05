package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Category;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.domain.ProcessStep;
import atig.recruiting.beta.domain.SubCategory;
import atig.recruiting.beta.domain.Tag;
import atig.recruiting.beta.service.dto.CategoryDTO;
import atig.recruiting.beta.service.dto.CompanyDTO;
import atig.recruiting.beta.service.dto.ProcessDTO;
import atig.recruiting.beta.service.dto.ProcessStepDTO;
import atig.recruiting.beta.service.dto.SubCategoryDTO;
import atig.recruiting.beta.service.dto.TagDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {
    @Mapping(target = "companyCategory", source = "companyCategory", qualifiedByName = "categoryId")
    @Mapping(target = "companySubCategory", source = "companySubCategory", qualifiedByName = "subCategoryId")
    @Mapping(target = "companyProcess", source = "companyProcess", qualifiedByName = "processId")
    @Mapping(target = "companyProcessStep", source = "companyProcessStep", qualifiedByName = "processStepId")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagIdSet")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "subCategory", source = "subCategory", qualifiedByName = "subCategoryId")
    @Mapping(target = "process", source = "process", qualifiedByName = "processId")
    @Mapping(target = "processStep", source = "processStep", qualifiedByName = "processStepId")
    CompanyDTO toDto(Company s);

    @Mapping(target = "removeTags", ignore = true)
    Company toEntity(CompanyDTO companyDTO);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("subCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubCategoryDTO toDtoSubCategoryId(SubCategory subCategory);

    @Named("processId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessDTO toDtoProcessId(Process process);

    @Named("processStepId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessStepDTO toDtoProcessStepId(ProcessStep processStep);

    @Named("tagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TagDTO toDtoTagId(Tag tag);

    @Named("tagIdSet")
    default Set<TagDTO> toDtoTagIdSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagId).collect(Collectors.toSet());
    }
}
