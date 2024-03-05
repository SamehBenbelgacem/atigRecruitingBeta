// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import { ICandidate, NewCandidate } from '../candidate.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts ICandidate for edit and NewCandidateFormGroupInput for create.
//  */
// type CandidateFormGroupInput = ICandidate | PartialWithRequiredKeyOf<NewCandidate>;
//
// type CandidateFormDefaults = Pick<NewCandidate, 'id' | 'tags'>;
//
// type CandidateFormGroupContent = {
//   id: FormControl<ICandidate['id'] | NewCandidate['id']>;
//   firstName: FormControl<ICandidate['firstName']>;
//   lastName: FormControl<ICandidate['lastName']>;
//   photo: FormControl<ICandidate['photo']>;
//   photoContentType: FormControl<ICandidate['photoContentType']>;
//   profession: FormControl<ICandidate['profession']>;
//   nbExperience: FormControl<ICandidate['nbExperience']>;
//   personalEmail: FormControl<ICandidate['personalEmail']>;
//   additionalInfos: FormControl<ICandidate['additionalInfos']>;
//   candidateCategory: FormControl<ICandidate['candidateCategory']>;
//   candidateSubCategory: FormControl<ICandidate['candidateSubCategory']>;
//   candidateProcess: FormControl<ICandidate['candidateProcess']>;
//   candidateProcessStep: FormControl<ICandidate['candidateProcessStep']>;
//   tags: FormControl<ICandidate['tags']>;
//   category: FormControl<ICandidate['category']>;
//   subCategory: FormControl<ICandidate['subCategory']>;
//   process: FormControl<ICandidate['process']>;
//   processStep: FormControl<ICandidate['processStep']>;
// };
//
// export type CandidateFormGroup = FormGroup<CandidateFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class CandidateFormService {
//   createCandidateFormGroup(candidate: CandidateFormGroupInput = { id: null }): CandidateFormGroup {
//     const candidateRawValue = {
//       ...this.getFormDefaults(),
//       ...candidate,
//     };
//     return new FormGroup<CandidateFormGroupContent>({
//       id: new FormControl(
//         { value: candidateRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       firstName: new FormControl(candidateRawValue.firstName, {
//         validators: [Validators.required],
//       }),
//       lastName: new FormControl(candidateRawValue.lastName, {
//         validators: [Validators.required],
//       }),
//       photo: new FormControl(candidateRawValue.photo),
//       photoContentType: new FormControl(candidateRawValue.photoContentType),
//       profession: new FormControl(candidateRawValue.profession, {
//         validators: [Validators.required],
//       }),
//       nbExperience: new FormControl(candidateRawValue.nbExperience),
//       personalEmail: new FormControl(candidateRawValue.personalEmail, {
//         validators: [Validators.required],
//       }),
//       additionalInfos: new FormControl(candidateRawValue.additionalInfos),
//       candidateCategory: new FormControl(candidateRawValue.candidateCategory),
//       candidateSubCategory: new FormControl(candidateRawValue.candidateSubCategory),
//       candidateProcess: new FormControl(candidateRawValue.candidateProcess),
//       candidateProcessStep: new FormControl(candidateRawValue.candidateProcessStep),
//       tags: new FormControl(candidateRawValue.tags ?? []),
//       category: new FormControl(candidateRawValue.category),
//       subCategory: new FormControl(candidateRawValue.subCategory),
//       process: new FormControl(candidateRawValue.process),
//       processStep: new FormControl(candidateRawValue.processStep),
//     });
//   }
//
//   getCandidate(form: CandidateFormGroup): ICandidate | NewCandidate {
//     return form.getRawValue() as ICandidate | NewCandidate;
//   }
//
//   resetForm(form: CandidateFormGroup, candidate: CandidateFormGroupInput): void {
//     const candidateRawValue = { ...this.getFormDefaults(), ...candidate };
//     form.reset(
//       {
//         ...candidateRawValue,
//         id: { value: candidateRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): CandidateFormDefaults {
//     return {
//       id: null,
//       tags: [],
//     };
//   }
// }
