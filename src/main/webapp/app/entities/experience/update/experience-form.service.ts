import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IExperience, NewExperience } from '../experience.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExperience for edit and NewExperienceFormGroupInput for create.
 */
type ExperienceFormGroupInput = IExperience | PartialWithRequiredKeyOf<NewExperience>;

type ExperienceFormDefaults = Pick<NewExperience, 'id'>;

type ExperienceFormGroupContent = {
  id: FormControl<IExperience['id'] | NewExperience['id']>;
  company: FormControl<IExperience['company']>;
  companySite: FormControl<IExperience['companySite']>;
  role: FormControl<IExperience['role']>;
  startDate: FormControl<IExperience['startDate']>;
  endDate: FormControl<IExperience['endDate']>;
  duration: FormControl<IExperience['duration']>;
  location: FormControl<IExperience['location']>;
  tasks: FormControl<IExperience['tasks']>;
  experienceCandidate: FormControl<IExperience['experienceCandidate']>;
  candidate: FormControl<IExperience['candidate']>;
};

export type ExperienceFormGroup = FormGroup<ExperienceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExperienceFormService {
  createExperienceFormGroup(experience: ExperienceFormGroupInput = { id: null }): ExperienceFormGroup {
    const experienceRawValue = {
      ...this.getFormDefaults(),
      ...experience,
    };
    return new FormGroup<ExperienceFormGroupContent>({
      id: new FormControl(
        { value: experienceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      company: new FormControl(experienceRawValue.company),
      companySite: new FormControl(experienceRawValue.companySite),
      role: new FormControl(experienceRawValue.role),
      startDate: new FormControl(experienceRawValue.startDate),
      endDate: new FormControl(experienceRawValue.endDate),
      duration: new FormControl(experienceRawValue.duration),
      location: new FormControl(experienceRawValue.location),
      tasks: new FormControl(experienceRawValue.tasks),
      experienceCandidate: new FormControl(experienceRawValue.experienceCandidate),
      candidate: new FormControl(experienceRawValue.candidate),
    });
  }

  getExperience(form: ExperienceFormGroup): IExperience | NewExperience {
    return form.getRawValue() as IExperience | NewExperience;
  }

  resetForm(form: ExperienceFormGroup, experience: ExperienceFormGroupInput): void {
    const experienceRawValue = { ...this.getFormDefaults(), ...experience };
    form.reset(
      {
        ...experienceRawValue,
        id: { value: experienceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ExperienceFormDefaults {
    return {
      id: null,
    };
  }
}
