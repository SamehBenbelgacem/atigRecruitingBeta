import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEducation, NewEducation } from '../education.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEducation for edit and NewEducationFormGroupInput for create.
 */
type EducationFormGroupInput = IEducation | PartialWithRequiredKeyOf<NewEducation>;

type EducationFormDefaults = Pick<NewEducation, 'id'>;

type EducationFormGroupContent = {
  id: FormControl<IEducation['id'] | NewEducation['id']>;
  diploma: FormControl<IEducation['diploma']>;
  establishment: FormControl<IEducation['establishment']>;
  mention: FormControl<IEducation['mention']>;
  startDate: FormControl<IEducation['startDate']>;
  endDate: FormControl<IEducation['endDate']>;
  duration: FormControl<IEducation['duration']>;
  location: FormControl<IEducation['location']>;
  educationCandidate: FormControl<IEducation['educationCandidate']>;
  candidate: FormControl<IEducation['candidate']>;
};

export type EducationFormGroup = FormGroup<EducationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EducationFormService {
  createEducationFormGroup(education: EducationFormGroupInput = { id: null }): EducationFormGroup {
    const educationRawValue = {
      ...this.getFormDefaults(),
      ...education,
    };
    return new FormGroup<EducationFormGroupContent>({
      id: new FormControl(
        { value: educationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      diploma: new FormControl(educationRawValue.diploma),
      establishment: new FormControl(educationRawValue.establishment),
      mention: new FormControl(educationRawValue.mention),
      startDate: new FormControl(educationRawValue.startDate),
      endDate: new FormControl(educationRawValue.endDate),
      duration: new FormControl(educationRawValue.duration),
      location: new FormControl(educationRawValue.location),
      educationCandidate: new FormControl(educationRawValue.educationCandidate),
      candidate: new FormControl(educationRawValue.candidate),
    });
  }

  getEducation(form: EducationFormGroup): IEducation | NewEducation {
    return form.getRawValue() as IEducation | NewEducation;
  }

  resetForm(form: EducationFormGroup, education: EducationFormGroupInput): void {
    const educationRawValue = { ...this.getFormDefaults(), ...education };
    form.reset(
      {
        ...educationRawValue,
        id: { value: educationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EducationFormDefaults {
    return {
      id: null,
    };
  }
}
