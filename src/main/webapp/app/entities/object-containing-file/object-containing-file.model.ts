import { ICandidateAdditionalInfos } from 'app/entities/candidate-additional-infos/candidate-additional-infos.model';
import { INote } from 'app/entities/note/note.model';
import { IEmail } from 'app/entities/email/email.model';

export interface IObjectContainingFile {
  id: number;
  file?: string | null;
  fileContentType?: string | null;
  candidateDocs?: Pick<ICandidateAdditionalInfos, 'id'> | null;
  noteDocs?: Pick<INote, 'id'> | null;
  emailDocs?: Pick<IEmail, 'id'> | null;
  candidateAdditionalInfos?: Pick<ICandidateAdditionalInfos, 'id'> | null;
  note?: Pick<INote, 'id'> | null;
  email?: Pick<IEmail, 'id'> | null;
}

export type NewObjectContainingFile = Omit<IObjectContainingFile, 'id'> & { id: null };
