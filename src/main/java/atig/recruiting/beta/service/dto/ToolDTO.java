package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Tool} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ToolDTO implements Serializable {

    private Long id;

    private String tool;

    private ExperienceDTO toolExperience;

    private ExperienceDTO experience;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public ExperienceDTO getToolExperience() {
        return toolExperience;
    }

    public void setToolExperience(ExperienceDTO toolExperience) {
        this.toolExperience = toolExperience;
    }

    public ExperienceDTO getExperience() {
        return experience;
    }

    public void setExperience(ExperienceDTO experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ToolDTO)) {
            return false;
        }

        ToolDTO toolDTO = (ToolDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, toolDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ToolDTO{" +
            "id=" + getId() +
            ", tool='" + getTool() + "'" +
            ", toolExperience=" + getToolExperience() +
            ", experience=" + getExperience() +
            "}";
    }
}
