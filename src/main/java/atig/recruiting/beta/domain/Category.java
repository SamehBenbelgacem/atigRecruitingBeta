package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonIgnoreProperties(value = { "candidates", "companies", "subCategoryCategory", "category" }, allowSetters = true)
    private Set<SubCategory> subCategories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Category title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SubCategory> getSubCategories() {
        return this.subCategories;
    }

    public void setSubCategories(Set<SubCategory> subCategories) {
        if (this.subCategories != null) {
            this.subCategories.forEach(i -> i.setCategory(null));
        }
        if (subCategories != null) {
            subCategories.forEach(i -> i.setCategory(this));
        }
        this.subCategories = subCategories;
    }

    public Category subCategories(Set<SubCategory> subCategories) {
        this.setSubCategories(subCategories);
        return this;
    }

    public Category addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
        subCategory.setCategory(this);
        return this;
    }

    public Category removeSubCategory(SubCategory subCategory) {
        this.subCategories.remove(subCategory);
        subCategory.setCategory(null);
        return this;
    }

    public Set<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        if (this.candidates != null) {
            this.candidates.forEach(i -> i.setCategory(null));
        }
        if (candidates != null) {
            candidates.forEach(i -> i.setCategory(this));
        }
        this.candidates = candidates;
    }

    public Category candidates(Set<Candidate> candidates) {
        this.setCandidates(candidates);
        return this;
    }

    public Category addCandidates(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setCategory(this);
        return this;
    }

    public Category removeCandidates(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.setCategory(null);
        return this;
    }

    public Set<Company> getCompanies() {
        return this.companies;
    }

    public void setCompanies(Set<Company> companies) {
        if (this.companies != null) {
            this.companies.forEach(i -> i.setCategory(null));
        }
        if (companies != null) {
            companies.forEach(i -> i.setCategory(this));
        }
        this.companies = companies;
    }

    public Category companies(Set<Company> companies) {
        this.setCompanies(companies);
        return this;
    }

    public Category addCompanies(Company company) {
        this.companies.add(company);
        company.setCategory(this);
        return this;
    }

    public Category removeCompanies(Company company) {
        this.companies.remove(company);
        company.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return getId() != null && getId().equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
