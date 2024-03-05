package atig.recruiting.beta.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.ObjectContainingFile} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObjectContainingFileDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] file;

    private String fileContentType;
    private CandidateAdditionalInfosDTO candidateDocs;

    private NoteDTO noteDocs;

    private EmailDTO emailDocs;

    private CandidateAdditionalInfosDTO candidateAdditionalInfos;

    private NoteDTO note;

    private EmailDTO email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public CandidateAdditionalInfosDTO getCandidateDocs() {
        return candidateDocs;
    }

    public void setCandidateDocs(CandidateAdditionalInfosDTO candidateDocs) {
        this.candidateDocs = candidateDocs;
    }

    public NoteDTO getNoteDocs() {
        return noteDocs;
    }

    public void setNoteDocs(NoteDTO noteDocs) {
        this.noteDocs = noteDocs;
    }

    public EmailDTO getEmailDocs() {
        return emailDocs;
    }

    public void setEmailDocs(EmailDTO emailDocs) {
        this.emailDocs = emailDocs;
    }

    public CandidateAdditionalInfosDTO getCandidateAdditionalInfos() {
        return candidateAdditionalInfos;
    }

    public void setCandidateAdditionalInfos(CandidateAdditionalInfosDTO candidateAdditionalInfos) {
        this.candidateAdditionalInfos = candidateAdditionalInfos;
    }

    public NoteDTO getNote() {
        return note;
    }

    public void setNote(NoteDTO note) {
        this.note = note;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public void setEmail(EmailDTO email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectContainingFileDTO)) {
            return false;
        }

        ObjectContainingFileDTO objectContainingFileDTO = (ObjectContainingFileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, objectContainingFileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjectContainingFileDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", candidateDocs=" + getCandidateDocs() +
            ", noteDocs=" + getNoteDocs() +
            ", emailDocs=" + getEmailDocs() +
            ", candidateAdditionalInfos=" + getCandidateAdditionalInfos() +
            ", note=" + getNote() +
            ", email=" + getEmail() +
            "}";
    }
}
