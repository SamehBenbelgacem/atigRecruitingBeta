package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Tool} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.ToolResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tools?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ToolCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tool;

    private LongFilter toolExperienceId;

    private LongFilter experienceId;

    private Boolean distinct;

    public ToolCriteria() {}

    public ToolCriteria(ToolCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tool = other.tool == null ? null : other.tool.copy();
        this.toolExperienceId = other.toolExperienceId == null ? null : other.toolExperienceId.copy();
        this.experienceId = other.experienceId == null ? null : other.experienceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ToolCriteria copy() {
        return new ToolCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTool() {
        return tool;
    }

    public StringFilter tool() {
        if (tool == null) {
            tool = new StringFilter();
        }
        return tool;
    }

    public void setTool(StringFilter tool) {
        this.tool = tool;
    }

    public LongFilter getToolExperienceId() {
        return toolExperienceId;
    }

    public LongFilter toolExperienceId() {
        if (toolExperienceId == null) {
            toolExperienceId = new LongFilter();
        }
        return toolExperienceId;
    }

    public void setToolExperienceId(LongFilter toolExperienceId) {
        this.toolExperienceId = toolExperienceId;
    }

    public LongFilter getExperienceId() {
        return experienceId;
    }

    public LongFilter experienceId() {
        if (experienceId == null) {
            experienceId = new LongFilter();
        }
        return experienceId;
    }

    public void setExperienceId(LongFilter experienceId) {
        this.experienceId = experienceId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ToolCriteria that = (ToolCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tool, that.tool) &&
            Objects.equals(toolExperienceId, that.toolExperienceId) &&
            Objects.equals(experienceId, that.experienceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tool, toolExperienceId, experienceId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ToolCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tool != null ? "tool=" + tool + ", " : "") +
            (toolExperienceId != null ? "toolExperienceId=" + toolExperienceId + ", " : "") +
            (experienceId != null ? "experienceId=" + experienceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
