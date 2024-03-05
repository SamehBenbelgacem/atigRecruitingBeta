package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Tool.
 */
@Entity
@Table(name = "tool")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tool")
    private String tool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tools", "experienceCandidate", "candidate" }, allowSetters = true)
    private Experience toolExperience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tools", "experienceCandidate", "candidate" }, allowSetters = true)
    private Experience experience;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tool id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTool() {
        return this.tool;
    }

    public Tool tool(String tool) {
        this.setTool(tool);
        return this;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public Experience getToolExperience() {
        return this.toolExperience;
    }

    public void setToolExperience(Experience experience) {
        this.toolExperience = experience;
    }

    public Tool toolExperience(Experience experience) {
        this.setToolExperience(experience);
        return this;
    }

    public Experience getExperience() {
        return this.experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Tool experience(Experience experience) {
        this.setExperience(experience);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tool)) {
            return false;
        }
        return getId() != null && getId().equals(((Tool) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tool{" +
            "id=" + getId() +
            ", tool='" + getTool() + "'" +
            "}";
    }
}
