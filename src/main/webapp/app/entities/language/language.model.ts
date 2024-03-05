import { ICandidate } from 'app/entities/candidate/candidate.model';
import { EnumLanguageName } from 'app/entities/enumerations/enum-language-name.model';
import { EnumLanguageLevel } from 'app/entities/enumerations/enum-language-level.model';

export interface ILanguage {
  id: number;
  name?: keyof typeof EnumLanguageName | null;
  level?: keyof typeof EnumLanguageLevel | null;
  languageCandidate?: Pick<ICandidate, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
}

export type NewLanguage = Omit<ILanguage, 'id'> & { id: null };
