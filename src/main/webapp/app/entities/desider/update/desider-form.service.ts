import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDesider, NewDesider } from '../desider.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDesider for edit and NewDesiderFormGroupInput for create.
 */
type DesiderFormGroupInput = IDesider | PartialWithRequiredKeyOf<NewDesider>;

type DesiderFormDefaults = Pick<NewDesider, 'id'>;

type DesiderFormGroupContent = {
  id: FormControl<IDesider['id'] | NewDesider['id']>;
  fullName: FormControl<IDesider['fullName']>;
  email: FormControl<IDesider['email']>;
  mobile: FormControl<IDesider['mobile']>;
  role: FormControl<IDesider['role']>;
  desiderCompany: FormControl<IDesider['desiderCompany']>;
  company: FormControl<IDesider['company']>;
};

export type DesiderFormGroup = FormGroup<DesiderFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DesiderFormService {
  createDesiderFormGroup(desider: DesiderFormGroupInput = { id: null }): DesiderFormGroup {
    const desiderRawValue = {
      ...this.getFormDefaults(),
      ...desider,
    };
    return new FormGroup<DesiderFormGroupContent>({
      id: new FormControl(
        { value: desiderRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fullName: new FormControl(desiderRawValue.fullName),
      email: new FormControl(desiderRawValue.email),
      mobile: new FormControl(desiderRawValue.mobile),
      role: new FormControl(desiderRawValue.role),
      desiderCompany: new FormControl(desiderRawValue.desiderCompany),
      company: new FormControl(desiderRawValue.company),
    });
  }

  getDesider(form: DesiderFormGroup): IDesider | NewDesider {
    return form.getRawValue() as IDesider | NewDesider;
  }

  resetForm(form: DesiderFormGroup, desider: DesiderFormGroupInput): void {
    const desiderRawValue = { ...this.getFormDefaults(), ...desider };
    form.reset(
      {
        ...desiderRawValue,
        id: { value: desiderRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DesiderFormDefaults {
    return {
      id: null,
    };
  }
}
