package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SubCategory.
 */
@Entity
@Table(name = "sub_category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategory")
    @JsonIgnoreProperties(
        value = {
            "additionalInfos",
            "experiences",
            "educations",
            "certifications",
            "skills",
            "languages",
            "notifications",
            "notes",
            "emails",
            "candidateCategory",
            "candidateSubCategory",
            "candidateProcess",
            "candidateProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Set<Candidate> candidates = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategory")
    @JsonIgnoreProperties(
        value = {
            "notifications",
            "notes",
            "desiders",
            "offers",
            "emails",
            "companyCategory",
            "companySubCategory",
            "companyProcess",
            "companyProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Set<Company> companies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subCategories", "candidates", "companies" }, allowSetters = true)
    private Category subCategoryCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subCategories", "candidates", "companies" }, allowSetters = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public SubCategory title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        if (this.candidates != null) {
            this.candidates.forEach(i -> i.setSubCategory(null));
        }
        if (candidates != null) {
            candidates.forEach(i -> i.setSubCategory(this));
        }
        this.candidates = candidates;
    }

    public SubCategory candidates(Set<Candidate> candidates) {
        this.setCandidates(candidates);
        return this;
    }

    public SubCategory addCandidates(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setSubCategory(this);
        return this;
    }

    public SubCategory removeCandidates(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.setSubCategory(null);
        return this;
    }

    public Set<Company> getCompanies() {
        return this.companies;
    }

    public void setCompanies(Set<Company> companies) {
        if (this.companies != null) {
            this.companies.forEach(i -> i.setSubCategory(null));
        }
        if (companies != null) {
            companies.forEach(i -> i.setSubCategory(this));
        }
        this.companies = companies;
    }

    public SubCategory companies(Set<Company> companies) {
        this.setCompanies(companies);
        return this;
    }

    public SubCategory addCompanies(Company company) {
        this.companies.add(company);
        company.setSubCategory(this);
        return this;
    }

    public SubCategory removeCompanies(Company company) {
        this.companies.remove(company);
        company.setSubCategory(null);
        return this;
    }

    public Category getSubCategoryCategory() {
        return this.subCategoryCategory;
    }

    public void setSubCategoryCategory(Category category) {
        this.subCategoryCategory = category;
    }

    public SubCategory subCategoryCategory(Category category) {
        this.setSubCategoryCategory(category);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory category(Category category) {
        this.setCategory(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((SubCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubCategory{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
