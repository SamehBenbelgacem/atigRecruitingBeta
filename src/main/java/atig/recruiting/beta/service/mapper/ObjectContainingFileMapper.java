package atig.recruiting.beta.service.mapper;

import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.Note;
import atig.recruiting.beta.domain.ObjectContainingFile;
import atig.recruiting.beta.service.dto.CandidateAdditionalInfosDTO;
import atig.recruiting.beta.service.dto.EmailDTO;
import atig.recruiting.beta.service.dto.NoteDTO;
import atig.recruiting.beta.service.dto.ObjectContainingFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ObjectContainingFile} and its DTO {@link ObjectContainingFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObjectContainingFileMapper extends EntityMapper<ObjectContainingFileDTO, ObjectContainingFile> {
    @Mapping(target = "candidateDocs", source = "candidateDocs", qualifiedByName = "candidateAdditionalInfosId")
    @Mapping(target = "noteDocs", source = "noteDocs", qualifiedByName = "noteId")
    @Mapping(target = "emailDocs", source = "emailDocs", qualifiedByName = "emailId")
    @Mapping(target = "candidateAdditionalInfos", source = "candidateAdditionalInfos", qualifiedByName = "candidateAdditionalInfosId")
    @Mapping(target = "note", source = "note", qualifiedByName = "noteId")
    @Mapping(target = "email", source = "email", qualifiedByName = "emailId")
    ObjectContainingFileDTO toDto(ObjectContainingFile s);

    @Named("candidateAdditionalInfosId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateAdditionalInfosDTO toDtoCandidateAdditionalInfosId(CandidateAdditionalInfos candidateAdditionalInfos);

    @Named("noteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NoteDTO toDtoNoteId(Note note);

    @Named("emailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmailDTO toDtoEmailId(Email email);
}
