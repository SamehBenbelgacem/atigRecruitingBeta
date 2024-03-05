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

class SubCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategory.class);
        SubCategory subCategory1 = getSubCategorySample1();
        SubCategory subCategory2 = new SubCategory();
        assertThat(subCategory1).isNotEqualTo(subCategory2);

        subCategory2.setId(subCategory1.getId());
        assertThat(subCategory1).isEqualTo(subCategory2);

        subCategory2 = getSubCategorySample2();
        assertThat(subCategory1).isNotEqualTo(subCategory2);
    }

    @Test
    void candidatesTest() throws Exception {
        SubCategory subCategory = getSubCategoryRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        subCategory.addCandidates(candidateBack);
        assertThat(subCategory.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getSubCategory()).isEqualTo(subCategory);

        subCategory.removeCandidates(candidateBack);
        assertThat(subCategory.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getSubCategory()).isNull();

        subCategory.candidates(new HashSet<>(Set.of(candidateBack)));
        assertThat(subCategory.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getSubCategory()).isEqualTo(subCategory);

        subCategory.setCandidates(new HashSet<>());
        assertThat(subCategory.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getSubCategory()).isNull();
    }

    @Test
    void companiesTest() throws Exception {
        SubCategory subCategory = getSubCategoryRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        subCategory.addCompanies(companyBack);
        assertThat(subCategory.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getSubCategory()).isEqualTo(subCategory);

        subCategory.removeCompanies(companyBack);
        assertThat(subCategory.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getSubCategory()).isNull();

        subCategory.companies(new HashSet<>(Set.of(companyBack)));
        assertThat(subCategory.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getSubCategory()).isEqualTo(subCategory);

        subCategory.setCompanies(new HashSet<>());
        assertThat(subCategory.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getSubCategory()).isNull();
    }

    @Test
    void subCategoryCategoryTest() throws Exception {
        SubCategory subCategory = getSubCategoryRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        subCategory.setSubCategoryCategory(categoryBack);
        assertThat(subCategory.getSubCategoryCategory()).isEqualTo(categoryBack);

        subCategory.subCategoryCategory(null);
        assertThat(subCategory.getSubCategoryCategory()).isNull();
    }

    @Test
    void categoryTest() throws Exception {
        SubCategory subCategory = getSubCategoryRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        subCategory.setCategory(categoryBack);
        assertThat(subCategory.getCategory()).isEqualTo(categoryBack);

        subCategory.category(null);
        assertThat(subCategory.getCategory()).isNull();
    }
}
