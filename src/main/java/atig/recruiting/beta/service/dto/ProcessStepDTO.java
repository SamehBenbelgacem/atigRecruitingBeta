package atig.recruiting.beta.service.dto;

import atig.recruiting.beta.domain.enumeration.EnumPriority;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.ProcessStep} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcessStepDTO implements Serializable {

    private Long id;

    private String title;

    private String order;

    private EnumPriority priority;

    private ProcessDTO processStepProcess;

    private ProcessDTO process;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public EnumPriority getPriority() {
        return priority;
    }

    public void setPriority(EnumPriority priority) {
        this.priority = priority;
    }

    public ProcessDTO getProcessStepProcess() {
        return processStepProcess;
    }

    public void setProcessStepProcess(ProcessDTO processStepProcess) {
        this.processStepProcess = processStepProcess;
    }

    public ProcessDTO getProcess() {
        return process;
    }

    public void setProcess(ProcessDTO process) {
        this.process = process;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessStepDTO)) {
            return false;
        }

        ProcessStepDTO processStepDTO = (ProcessStepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processStepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessStepDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", order='" + getOrder() + "'" +
            ", priority='" + getPriority() + "'" +
            ", processStepProcess=" + getProcessStepProcess() +
            ", process=" + getProcess() +
            "}";
    }
}
