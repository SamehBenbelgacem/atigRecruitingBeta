package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A CandidateAdditionalInfos.
 */
@Entity
@Table(name = "candidate_additional_infos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidateAdditionalInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "actual_salary")
    private Integer actualSalary;

    @Column(name = "expected_salary")
    private Integer expectedSalary;

    @Column(name = "first_contact")
    private Instant firstContact;

    @Column(name = "location")
    private String location;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "disponibility")
    private String disponibility;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateAdditionalInfos")
    @JsonIgnoreProperties(
        value = { "candidateDocs", "noteDocs", "emailDocs", "candidateAdditionalInfos", "note", "email" },
        allowSetters = true
    )
    private Set<ObjectContainingFile> documents = new HashSet<>();

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "additionalInfos")
    private Candidate candidate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CandidateAdditionalInfos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBirthday() {
        return this.birthday;
    }

    public CandidateAdditionalInfos birthday(Instant birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Integer getActualSalary() {
        return this.actualSalary;
    }

    public CandidateAdditionalInfos actualSalary(Integer actualSalary) {
        this.setActualSalary(actualSalary);
        return this;
    }

    public void setActualSalary(Integer actualSalary) {
        this.actualSalary = actualSalary;
    }

    public Integer getExpectedSalary() {
        return this.expectedSalary;
    }

    public CandidateAdditionalInfos expectedSalary(Integer expectedSalary) {
        this.setExpectedSalary(expectedSalary);
        return this;
    }

    public void setExpectedSalary(Integer expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public Instant getFirstContact() {
        return this.firstContact;
    }

    public CandidateAdditionalInfos firstContact(Instant firstContact) {
        this.setFirstContact(firstContact);
        return this;
    }

    public void setFirstContact(Instant firstContact) {
        this.firstContact = firstContact;
    }

    public String getLocation() {
        return this.location;
    }

    public CandidateAdditionalInfos location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return this.mobile;
    }

    public CandidateAdditionalInfos mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDisponibility() {
        return this.disponibility;
    }

    public CandidateAdditionalInfos disponibility(String disponibility) {
        this.setDisponibility(disponibility);
        return this;
    }

    public void setDisponibility(String disponibility) {
        this.disponibility = disponibility;
    }

    public Set<ObjectContainingFile> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<ObjectContainingFile> objectContainingFiles) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setCandidateAdditionalInfos(null));
        }
        if (objectContainingFiles != null) {
            objectContainingFiles.forEach(i -> i.setCandidateAdditionalInfos(this));
        }
        this.documents = objectContainingFiles;
    }

    public CandidateAdditionalInfos documents(Set<ObjectContainingFile> objectContainingFiles) {
        this.setDocuments(objectContainingFiles);
        return this;
    }

    public CandidateAdditionalInfos addDocuments(ObjectContainingFile objectContainingFile) {
        this.documents.add(objectContainingFile);
        objectContainingFile.setCandidateAdditionalInfos(this);
        return this;
    }

    public CandidateAdditionalInfos removeDocuments(ObjectContainingFile objectContainingFile) {
        this.documents.remove(objectContainingFile);
        objectContainingFile.setCandidateAdditionalInfos(null);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        if (this.candidate != null) {
            this.candidate.setAdditionalInfos(null);
        }
        if (candidate != null) {
            candidate.setAdditionalInfos(this);
        }
        this.candidate = candidate;
    }

    public CandidateAdditionalInfos candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateAdditionalInfos)) {
            return false;
        }
        return getId() != null && getId().equals(((CandidateAdditionalInfos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateAdditionalInfos{" +
            "id=" + getId() +
            ", birthday='" + getBirthday() + "'" +
            ", actualSalary=" + getActualSalary() +
            ", expectedSalary=" + getExpectedSalary() +
            ", firstContact='" + getFirstContact() + "'" +
            ", location='" + getLocation() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", disponibility='" + getDisponibility() + "'" +
            "}";
    }
}
