package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "profession", nullable = false)
    private String profession;

    @Column(name = "nb_experience")
    private Integer nbExperience;

    @NotNull
    @Column(name = "personal_email", nullable = false)
    private String personalEmail;

    @JsonIgnoreProperties(value = { "documents", "candidate" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CandidateAdditionalInfos additionalInfos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(value = { "tools", "experienceCandidate", "candidate" }, allowSetters = true)
    private Set<Experience> experiences = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(value = { "educationCandidate", "candidate" }, allowSetters = true)
    private Set<Education> educations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(value = { "certificationCandidate", "candidate" }, allowSetters = true)
    private Set<Certification> certifications = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(value = { "skillCandidate", "candidate" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(value = { "languageCandidate", "candidate" }, allowSetters = true)
    private Set<Language> languages = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(value = { "notificationCandidate", "notificationCompany", "candidate", "company" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(value = { "documents", "noteCompany", "noteCandidate", "candidate", "company" }, allowSetters = true)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @JsonIgnoreProperties(
        value = {
            "joinedFiles",
            "subEmails",
            "emailEmailcredentials",
            "emailCandidate",
            "emailCompany",
            "candidate",
            "company",
            "emailcredentials",
        },
        allowSetters = true
    )
    private Set<Email> emails = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subCategories", "candidates", "companies" }, allowSetters = true)
    private Category candidateCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "candidates", "companies", "subCategoryCategory", "category" }, allowSetters = true)
    private SubCategory candidateSubCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "steps", "candidates", "companies" }, allowSetters = true)
    private Process candidateProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "candidates", "companies", "processStepProcess", "process" }, allowSetters = true)
    private ProcessStep candidateProcessStep;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_candidate__tags",
        joinColumns = @JoinColumn(name = "candidate_id"),
        inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @JsonIgnoreProperties(value = { "companies", "offers", "candidates" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subCategories", "candidates", "companies" }, allowSetters = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "candidates", "companies", "subCategoryCategory", "category" }, allowSetters = true)
    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "steps", "candidates", "companies" }, allowSetters = true)
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "candidates", "companies", "processStepProcess", "process" }, allowSetters = true)
    private ProcessStep processStep;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Candidate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Candidate firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Candidate lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Candidate photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Candidate photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getProfession() {
        return this.profession;
    }

    public Candidate profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getNbExperience() {
        return this.nbExperience;
    }

    public Candidate nbExperience(Integer nbExperience) {
        this.setNbExperience(nbExperience);
        return this;
    }

    public void setNbExperience(Integer nbExperience) {
        this.nbExperience = nbExperience;
    }

    public String getPersonalEmail() {
        return this.personalEmail;
    }

    public Candidate personalEmail(String personalEmail) {
        this.setPersonalEmail(personalEmail);
        return this;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public CandidateAdditionalInfos getAdditionalInfos() {
        return this.additionalInfos;
    }

    public void setAdditionalInfos(CandidateAdditionalInfos candidateAdditionalInfos) {
        this.additionalInfos = candidateAdditionalInfos;
    }

    public Candidate additionalInfos(CandidateAdditionalInfos candidateAdditionalInfos) {
        this.setAdditionalInfos(candidateAdditionalInfos);
        return this;
    }

    public Set<Experience> getExperiences() {
        return this.experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        if (this.experiences != null) {
            this.experiences.forEach(i -> i.setCandidate(null));
        }
        if (experiences != null) {
            experiences.forEach(i -> i.setCandidate(this));
        }
        this.experiences = experiences;
    }

    public Candidate experiences(Set<Experience> experiences) {
        this.setExperiences(experiences);
        return this;
    }

    public Candidate addExperiences(Experience experience) {
        this.experiences.add(experience);
        experience.setCandidate(this);
        return this;
    }

    public Candidate removeExperiences(Experience experience) {
        this.experiences.remove(experience);
        experience.setCandidate(null);
        return this;
    }

    public Set<Education> getEducations() {
        return this.educations;
    }

    public void setEducations(Set<Education> educations) {
        if (this.educations != null) {
            this.educations.forEach(i -> i.setCandidate(null));
        }
        if (educations != null) {
            educations.forEach(i -> i.setCandidate(this));
        }
        this.educations = educations;
    }

    public Candidate educations(Set<Education> educations) {
        this.setEducations(educations);
        return this;
    }

    public Candidate addEducations(Education education) {
        this.educations.add(education);
        education.setCandidate(this);
        return this;
    }

    public Candidate removeEducations(Education education) {
        this.educations.remove(education);
        education.setCandidate(null);
        return this;
    }

    public Set<Certification> getCertifications() {
        return this.certifications;
    }

    public void setCertifications(Set<Certification> certifications) {
        if (this.certifications != null) {
            this.certifications.forEach(i -> i.setCandidate(null));
        }
        if (certifications != null) {
            certifications.forEach(i -> i.setCandidate(this));
        }
        this.certifications = certifications;
    }

    public Candidate certifications(Set<Certification> certifications) {
        this.setCertifications(certifications);
        return this;
    }

    public Candidate addCertifications(Certification certification) {
        this.certifications.add(certification);
        certification.setCandidate(this);
        return this;
    }

    public Candidate removeCertifications(Certification certification) {
        this.certifications.remove(certification);
        certification.setCandidate(null);
        return this;
    }

    public Set<Skill> getSkills() {
        return this.skills;
    }

    public void setSkills(Set<Skill> skills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setCandidate(null));
        }
        if (skills != null) {
            skills.forEach(i -> i.setCandidate(this));
        }
        this.skills = skills;
    }

    public Candidate skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    public Candidate addSkills(Skill skill) {
        this.skills.add(skill);
        skill.setCandidate(this);
        return this;
    }

    public Candidate removeSkills(Skill skill) {
        this.skills.remove(skill);
        skill.setCandidate(null);
        return this;
    }

    public Set<Language> getLanguages() {
        return this.languages;
    }

    public void setLanguages(Set<Language> languages) {
        if (this.languages != null) {
            this.languages.forEach(i -> i.setCandidate(null));
        }
        if (languages != null) {
            languages.forEach(i -> i.setCandidate(this));
        }
        this.languages = languages;
    }

    public Candidate languages(Set<Language> languages) {
        this.setLanguages(languages);
        return this;
    }

    public Candidate addLanguages(Language language) {
        this.languages.add(language);
        language.setCandidate(this);
        return this;
    }

    public Candidate removeLanguages(Language language) {
        this.languages.remove(language);
        language.setCandidate(null);
        return this;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setCandidate(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setCandidate(this));
        }
        this.notifications = notifications;
    }

    public Candidate notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public Candidate addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setCandidate(this);
        return this;
    }

    public Candidate removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setCandidate(null);
        return this;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(Set<Note> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setCandidate(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setCandidate(this));
        }
        this.notes = notes;
    }

    public Candidate notes(Set<Note> notes) {
        this.setNotes(notes);
        return this;
    }

    public Candidate addNotes(Note note) {
        this.notes.add(note);
        note.setCandidate(this);
        return this;
    }

    public Candidate removeNotes(Note note) {
        this.notes.remove(note);
        note.setCandidate(null);
        return this;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setCandidate(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setCandidate(this));
        }
        this.emails = emails;
    }

    public Candidate emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public Candidate addEmails(Email email) {
        this.emails.add(email);
        email.setCandidate(this);
        return this;
    }

    public Candidate removeEmails(Email email) {
        this.emails.remove(email);
        email.setCandidate(null);
        return this;
    }

    public Category getCandidateCategory() {
        return this.candidateCategory;
    }

    public void setCandidateCategory(Category category) {
        this.candidateCategory = category;
    }

    public Candidate candidateCategory(Category category) {
        this.setCandidateCategory(category);
        return this;
    }

    public SubCategory getCandidateSubCategory() {
        return this.candidateSubCategory;
    }

    public void setCandidateSubCategory(SubCategory subCategory) {
        this.candidateSubCategory = subCategory;
    }

    public Candidate candidateSubCategory(SubCategory subCategory) {
        this.setCandidateSubCategory(subCategory);
        return this;
    }

    public Process getCandidateProcess() {
        return this.candidateProcess;
    }

    public void setCandidateProcess(Process process) {
        this.candidateProcess = process;
    }

    public Candidate candidateProcess(Process process) {
        this.setCandidateProcess(process);
        return this;
    }

    public ProcessStep getCandidateProcessStep() {
        return this.candidateProcessStep;
    }

    public void setCandidateProcessStep(ProcessStep processStep) {
        this.candidateProcessStep = processStep;
    }

    public Candidate candidateProcessStep(ProcessStep processStep) {
        this.setCandidateProcessStep(processStep);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Candidate tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Candidate addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Candidate removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Candidate category(Category category) {
        this.setCategory(category);
        return this;
    }

    public SubCategory getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Candidate subCategory(SubCategory subCategory) {
        this.setSubCategory(subCategory);
        return this;
    }

    public Process getProcess() {
        return this.process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Candidate process(Process process) {
        this.setProcess(process);
        return this;
    }

    public ProcessStep getProcessStep() {
        return this.processStep;
    }

    public void setProcessStep(ProcessStep processStep) {
        this.processStep = processStep;
    }

    public Candidate processStep(ProcessStep processStep) {
        this.setProcessStep(processStep);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return getId() != null && getId().equals(((Candidate) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", profession='" + getProfession() + "'" +
            ", nbExperience=" + getNbExperience() +
            ", personalEmail='" + getPersonalEmail() + "'" +
            "}";
    }
}
