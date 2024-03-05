package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.SubCategory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubCategoryDTO implements Serializable {

    private Long id;

    private String title;

    private CategoryDTO subCategoryCategory;

    private CategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryDTO getSubCategoryCategory() {
        return subCategoryCategory;
    }

    public void setSubCategoryCategory(CategoryDTO subCategoryCategory) {
        this.subCategoryCategory = subCategoryCategory;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubCategoryDTO)) {
            return false;
        }

        SubCategoryDTO subCategoryDTO = (SubCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubCategoryDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", subCategoryCategory=" + getSubCategoryCategory() +
            ", category=" + getCategory() +
            "}";
    }
}
