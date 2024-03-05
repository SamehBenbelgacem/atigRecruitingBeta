package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Category;
import atig.recruiting.beta.domain.SubCategory;
import atig.recruiting.beta.service.dto.CategoryDTO;
import atig.recruiting.beta.service.dto.SubCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubCategory} and its DTO {@link SubCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubCategoryMapper extends EntityMapper<SubCategoryDTO, SubCategory> {
    @Mapping(target = "subCategoryCategory", source = "subCategoryCategory", qualifiedByName = "categoryId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    SubCategoryDTO toDto(SubCategory s);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
