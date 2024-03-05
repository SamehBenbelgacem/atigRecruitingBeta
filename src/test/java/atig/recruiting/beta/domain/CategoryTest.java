package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CategoryTestSamples.*;
import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.SubCategoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void subCategoryTest() throws Exception {
        Category category = getCategoryRandomSampleGenerator();
        SubCategory subCategoryBack = getSubCategoryRandomSampleGenerator();

        category.addSubCategory(subCategoryBack);
        assertThat(category.getSubCategories()).containsOnly(subCategoryBack);
        assertThat(subCategoryBack.getCategory()).isEqualTo(category);

        category.removeSubCategory(subCategoryBack);
        assertThat(category.getSubCategories()).doesNotContain(subCategoryBack);
        assertThat(subCategoryBack.getCategory()).isNull();

        category.subCategories(new HashSet<>(Set.of(subCategoryBack)));
        assertThat(category.getSubCategories()).containsOnly(subCategoryBack);
        assertThat(subCategoryBack.getCategory()).isEqualTo(category);

        category.setSubCategories(new HashSet<>());
        assertThat(category.getSubCategories()).doesNotContain(subCategoryBack);
        assertThat(subCategoryBack.getCategory()).isNull();
    }

    @Test
    void candidatesTest() throws Exception {
        Category category = getCategoryRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        category.addCandidates(candidateBack);
        assertThat(category.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getCategory()).isEqualTo(category);

        category.removeCandidates(candidateBack);
        assertThat(category.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getCategory()).isNull();

        category.candidates(new HashSet<>(Set.of(candidateBack)));
        assertThat(category.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getCategory()).isEqualTo(category);

        category.setCandidates(new HashSet<>());
        assertThat(category.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getCategory()).isNull();
    }

    @Test
    void companiesTest() throws Exception {
        Category category = getCategoryRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        category.addCompanies(companyBack);
        assertThat(category.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getCategory()).isEqualTo(category);

        category.removeCompanies(companyBack);
        assertThat(category.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getCategory()).isNull();

        category.companies(new HashSet<>(Set.of(companyBack)));
        assertThat(category.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getCategory()).isEqualTo(category);

        category.setCompanies(new HashSet<>());
        assertThat(category.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getCategory()).isNull();
    }
}
