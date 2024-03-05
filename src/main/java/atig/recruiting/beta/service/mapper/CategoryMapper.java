package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.Category;
import atig.recruiting.beta.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {}
