package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "speciality")
    private String speciality;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @Column(name = "location")
    private String location;

    @Column(name = "info_email")
    private String infoEmail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "first_contact_date")
    private LocalDate firstContactDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonIgnoreProperties(value = { "notificationCandidate", "notificationCompany", "candidate", "company" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonIgnoreProperties(value = { "documents", "noteCompany", "noteCandidate", "candidate", "company" }, allowSetters = true)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonIgnoreProperties(value = { "desiderCompany", "company" }, allowSetters = true)
    private Set<Desider> desiders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    @JsonIgnoreProperties(value = { "offerCompany", "tags", "company" }, allowSetters = true)
    private Set<Offer> offers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
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
    private Category companyCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "candidates", "companies", "subCategoryCategory", "category" }, allowSetters = true)
    private SubCategory companySubCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "steps", "candidates", "companies" }, allowSetters = true)
    private Process companyProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "candidates", "companies", "processStepProcess", "process" }, allowSetters = true)
    private ProcessStep companyProcessStep;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_company__tags",
        joinColumns = @JoinColumn(name = "company_id"),
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

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return this.speciality;
    }

    public Company speciality(String speciality) {
        this.setSpeciality(speciality);
        return this;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Company logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Company logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getDescription() {
        return this.description;
    }

    public Company description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return this.website;
    }

    public Company website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return this.location;
    }

    public Company location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfoEmail() {
        return this.infoEmail;
    }

    public Company infoEmail(String infoEmail) {
        this.setInfoEmail(infoEmail);
        return this;
    }

    public void setInfoEmail(String infoEmail) {
        this.infoEmail = infoEmail;
    }

    public String getPhone() {
        return this.phone;
    }

    public Company phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getFirstContactDate() {
        return this.firstContactDate;
    }

    public Company firstContactDate(LocalDate firstContactDate) {
        this.setFirstContactDate(firstContactDate);
        return this;
    }

    public void setFirstContactDate(LocalDate firstContactDate) {
        this.firstContactDate = firstContactDate;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setCompany(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setCompany(this));
        }
        this.notifications = notifications;
    }

    public Company notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public Company addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setCompany(this);
        return this;
    }

    public Company removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setCompany(null);
        return this;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(Set<Note> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setCompany(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setCompany(this));
        }
        this.notes = notes;
    }

    public Company notes(Set<Note> notes) {
        this.setNotes(notes);
        return this;
    }

    public Company addNotes(Note note) {
        this.notes.add(note);
        note.setCompany(this);
        return this;
    }

    public Company removeNotes(Note note) {
        this.notes.remove(note);
        note.setCompany(null);
        return this;
    }

    public Set<Desider> getDesiders() {
        return this.desiders;
    }

    public void setDesiders(Set<Desider> desiders) {
        if (this.desiders != null) {
            this.desiders.forEach(i -> i.setCompany(null));
        }
        if (desiders != null) {
            desiders.forEach(i -> i.setCompany(this));
        }
        this.desiders = desiders;
    }

    public Company desiders(Set<Desider> desiders) {
        this.setDesiders(desiders);
        return this;
    }

    public Company addDesiders(Desider desider) {
        this.desiders.add(desider);
        desider.setCompany(this);
        return this;
    }

    public Company removeDesiders(Desider desider) {
        this.desiders.remove(desider);
        desider.setCompany(null);
        return this;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(Set<Offer> offers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.setCompany(null));
        }
        if (offers != null) {
            offers.forEach(i -> i.setCompany(this));
        }
        this.offers = offers;
    }

    public Company offers(Set<Offer> offers) {
        this.setOffers(offers);
        return this;
    }

    public Company addOffers(Offer offer) {
        this.offers.add(offer);
        offer.setCompany(this);
        return this;
    }

    public Company removeOffers(Offer offer) {
        this.offers.remove(offer);
        offer.setCompany(null);
        return this;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setCompany(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setCompany(this));
        }
        this.emails = emails;
    }

    public Company emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public Company addEmails(Email email) {
        this.emails.add(email);
        email.setCompany(this);
        return this;
    }

    public Company removeEmails(Email email) {
        this.emails.remove(email);
        email.setCompany(null);
        return this;
    }

    public Category getCompanyCategory() {
        return this.companyCategory;
    }

    public void setCompanyCategory(Category category) {
        this.companyCategory = category;
    }

    public Company companyCategory(Category category) {
        this.setCompanyCategory(category);
        return this;
    }

    public SubCategory getCompanySubCategory() {
        return this.companySubCategory;
    }

    public void setCompanySubCategory(SubCategory subCategory) {
        this.companySubCategory = subCategory;
    }

    public Company companySubCategory(SubCategory subCategory) {
        this.setCompanySubCategory(subCategory);
        return this;
    }

    public Process getCompanyProcess() {
        return this.companyProcess;
    }

    public void setCompanyProcess(Process process) {
        this.companyProcess = process;
    }

    public Company companyProcess(Process process) {
        this.setCompanyProcess(process);
        return this;
    }

    public ProcessStep getCompanyProcessStep() {
        return this.companyProcessStep;
    }

    public void setCompanyProcessStep(ProcessStep processStep) {
        this.companyProcessStep = processStep;
    }

    public Company companyProcessStep(ProcessStep processStep) {
        this.setCompanyProcessStep(processStep);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Company tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Company addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Company removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Company category(Category category) {
        this.setCategory(category);
        return this;
    }

    public SubCategory getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Company subCategory(SubCategory subCategory) {
        this.setSubCategory(subCategory);
        return this;
    }

    public Process getProcess() {
        return this.process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Company process(Process process) {
        this.setProcess(process);
        return this;
    }

    public ProcessStep getProcessStep() {
        return this.processStep;
    }

    public void setProcessStep(ProcessStep processStep) {
        this.processStep = processStep;
    }

    public Company processStep(ProcessStep processStep) {
        this.setProcessStep(processStep);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return getId() != null && getId().equals(((Company) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", speciality='" + getSpeciality() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", website='" + getWebsite() + "'" +
            ", location='" + getLocation() + "'" +
            ", infoEmail='" + getInfoEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", firstContactDate='" + getFirstContactDate() + "'" +
            "}";
    }
}
