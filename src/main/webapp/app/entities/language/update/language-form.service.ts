import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILanguage, NewLanguage } from '../language.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILanguage for edit and NewLanguageFormGroupInput for create.
 */
type LanguageFormGroupInput = ILanguage | PartialWithRequiredKeyOf<NewLanguage>;

type LanguageFormDefaults = Pick<NewLanguage, 'id'>;

type LanguageFormGroupContent = {
  id: FormControl<ILanguage['id'] | NewLanguage['id']>;
  name: FormControl<ILanguage['name']>;
  level: FormControl<ILanguage['level']>;
  languageCandidate: FormControl<ILanguage['languageCandidate']>;
  candidate: FormControl<ILanguage['candidate']>;
};

export type LanguageFormGroup = FormGroup<LanguageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LanguageFormService {
  createLanguageFormGroup(language: LanguageFormGroupInput = { id: null }): LanguageFormGroup {
    const languageRawValue = {
      ...this.getFormDefaults(),
      ...language,
    };
    return new FormGroup<LanguageFormGroupContent>({
      id: new FormControl(
        { value: languageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(languageRawValue.name),
      level: new FormControl(languageRawValue.level),
      languageCandidate: new FormControl(languageRawValue.languageCandidate),
      candidate: new FormControl(languageRawValue.candidate),
    });
  }

  getLanguage(form: LanguageFormGroup): ILanguage | NewLanguage {
    return form.getRawValue() as ILanguage | NewLanguage;
  }

  resetForm(form: LanguageFormGroup, language: LanguageFormGroupInput): void {
    const languageRawValue = { ...this.getFormDefaults(), ...language };
    form.reset(
      {
        ...languageRawValue,
        id: { value: languageRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LanguageFormDefaults {
    return {
      id: null,
    };
  }
}
