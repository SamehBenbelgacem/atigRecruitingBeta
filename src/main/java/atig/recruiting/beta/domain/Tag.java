package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonIgnoreProperties(value = { "offerCompany", "tags", "company" }, allowSetters = true)
    private Set<Offer> offers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Tag title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Company> getCompanies() {
        return this.companies;
    }

    public void setCompanies(Set<Company> companies) {
        if (this.companies != null) {
            this.companies.forEach(i -> i.removeTags(this));
        }
        if (companies != null) {
            companies.forEach(i -> i.addTags(this));
        }
        this.companies = companies;
    }

    public Tag companies(Set<Company> companies) {
        this.setCompanies(companies);
        return this;
    }

    public Tag addCompanies(Company company) {
        this.companies.add(company);
        company.getTags().add(this);
        return this;
    }

    public Tag removeCompanies(Company company) {
        this.companies.remove(company);
        company.getTags().remove(this);
        return this;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(Set<Offer> offers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.removeTags(this));
        }
        if (offers != null) {
            offers.forEach(i -> i.addTags(this));
        }
        this.offers = offers;
    }

    public Tag offers(Set<Offer> offers) {
        this.setOffers(offers);
        return this;
    }

    public Tag addOffers(Offer offer) {
        this.offers.add(offer);
        offer.getTags().add(this);
        return this;
    }

    public Tag removeOffers(Offer offer) {
        this.offers.remove(offer);
        offer.getTags().remove(this);
        return this;
    }

    public Set<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        if (this.candidates != null) {
            this.candidates.forEach(i -> i.removeTags(this));
        }
        if (candidates != null) {
            candidates.forEach(i -> i.addTags(this));
        }
        this.candidates = candidates;
    }

    public Tag candidates(Set<Candidate> candidates) {
        this.setCandidates(candidates);
        return this;
    }

    public Tag addCandidates(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.getTags().add(this);
        return this;
    }

    public Tag removeCandidates(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.getTags().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return getId() != null && getId().equals(((Tag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
