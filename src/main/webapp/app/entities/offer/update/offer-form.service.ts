import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOffer, NewOffer } from '../offer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOffer for edit and NewOfferFormGroupInput for create.
 */
type OfferFormGroupInput = IOffer | PartialWithRequiredKeyOf<NewOffer>;

type OfferFormDefaults = Pick<NewOffer, 'id' | 'tags'>;

type OfferFormGroupContent = {
  id: FormControl<IOffer['id'] | NewOffer['id']>;
  title: FormControl<IOffer['title']>;
  description: FormControl<IOffer['description']>;
  date: FormControl<IOffer['date']>;
  offerCompany: FormControl<IOffer['offerCompany']>;
  tags: FormControl<IOffer['tags']>;
  company: FormControl<IOffer['company']>;
};

export type OfferFormGroup = FormGroup<OfferFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OfferFormService {
  createOfferFormGroup(offer: OfferFormGroupInput = { id: null }): OfferFormGroup {
    const offerRawValue = {
      ...this.getFormDefaults(),
      ...offer,
    };
    return new FormGroup<OfferFormGroupContent>({
      id: new FormControl(
        { value: offerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(offerRawValue.title),
      description: new FormControl(offerRawValue.description),
      date: new FormControl(offerRawValue.date),
      offerCompany: new FormControl(offerRawValue.offerCompany),
      tags: new FormControl(offerRawValue.tags ?? []),
      company: new FormControl(offerRawValue.company),
    });
  }

  getOffer(form: OfferFormGroup): IOffer | NewOffer {
    return form.getRawValue() as IOffer | NewOffer;
  }

  resetForm(form: OfferFormGroup, offer: OfferFormGroupInput): void {
    const offerRawValue = { ...this.getFormDefaults(), ...offer };
    form.reset(
      {
        ...offerRawValue,
        id: { value: offerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OfferFormDefaults {
    return {
      id: null,
      tags: [],
    };
  }
}
