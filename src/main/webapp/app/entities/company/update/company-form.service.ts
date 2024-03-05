// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import { ICompany, NewCompany } from '../company.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts ICompany for edit and NewCompanyFormGroupInput for create.
//  */
// type CompanyFormGroupInput = ICompany | PartialWithRequiredKeyOf<NewCompany>;
//
// type CompanyFormDefaults = Pick<NewCompany, 'id' | 'tags'>;
//
// type CompanyFormGroupContent = {
//   id: FormControl<ICompany['id'] | NewCompany['id']>;
//   name: FormControl<ICompany['name']>;
//   speciality: FormControl<ICompany['speciality']>;
//   logo: FormControl<ICompany['logo']>;
//   logoContentType: FormControl<ICompany['logoContentType']>;
//   description: FormControl<ICompany['description']>;
//   website: FormControl<ICompany['website']>;
//   location: FormControl<ICompany['location']>;
//   infoEmail: FormControl<ICompany['infoEmail']>;
//   phone: FormControl<ICompany['phone']>;
//   firstContactDate: FormControl<ICompany['firstContactDate']>;
//   companyCategory: FormControl<ICompany['companyCategory']>;
//   companySubCategory: FormControl<ICompany['companySubCategory']>;
//   companyProcess: FormControl<ICompany['companyProcess']>;
//   companyProcessStep: FormControl<ICompany['companyProcessStep']>;
//   tags: FormControl<ICompany['tags']>;
//   category: FormControl<ICompany['category']>;
//   subCategory: FormControl<ICompany['subCategory']>;
//   process: FormControl<ICompany['process']>;
//   processStep: FormControl<ICompany['processStep']>;
// };
//
// export type CompanyFormGroup = FormGroup<CompanyFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class CompanyFormService {
//   createCompanyFormGroup(company: CompanyFormGroupInput = { id: null }): CompanyFormGroup {
//     const companyRawValue = {
//       ...this.getFormDefaults(),
//       ...company,
//     };
//     return new FormGroup<CompanyFormGroupContent>({
//       id: new FormControl(
//         { value: companyRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       name: new FormControl(companyRawValue.name),
//       speciality: new FormControl(companyRawValue.speciality),
//       logo: new FormControl(companyRawValue.logo),
//       logoContentType: new FormControl(companyRawValue.logoContentType),
//       description: new FormControl(companyRawValue.description),
//       website: new FormControl(companyRawValue.website),
//       location: new FormControl(companyRawValue.location),
//       infoEmail: new FormControl(companyRawValue.infoEmail),
//       phone: new FormControl(companyRawValue.phone),
//       firstContactDate: new FormControl(companyRawValue.firstContactDate),
//       companyCategory: new FormControl(companyRawValue.companyCategory),
//       companySubCategory: new FormControl(companyRawValue.companySubCategory),
//       companyProcess: new FormControl(companyRawValue.companyProcess),
//       companyProcessStep: new FormControl(companyRawValue.companyProcessStep),
//       tags: new FormControl(companyRawValue.tags ?? []),
//       category: new FormControl(companyRawValue.category),
//       subCategory: new FormControl(companyRawValue.subCategory),
//       process: new FormControl(companyRawValue.process),
//       processStep: new FormControl(companyRawValue.processStep),
//     });
//   }
//
//   getCompany(form: CompanyFormGroup): ICompany | NewCompany {
//     return form.getRawValue() as ICompany | NewCompany;
//   }
//
//   resetForm(form: CompanyFormGroup, company: CompanyFormGroupInput): void {
//     const companyRawValue = { ...this.getFormDefaults(), ...company };
//     form.reset(
//       {
//         ...companyRawValue,
//         id: { value: companyRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): CompanyFormDefaults {
//     return {
//       id: null,
//       tags: [],
//     };
//   }
// }
