// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import { IObjectContainingFile, NewObjectContainingFile } from '../object-containing-file.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts IObjectContainingFile for edit and NewObjectContainingFileFormGroupInput for create.
//  */
// type ObjectContainingFileFormGroupInput = IObjectContainingFile | PartialWithRequiredKeyOf<NewObjectContainingFile>;
//
// type ObjectContainingFileFormDefaults = Pick<NewObjectContainingFile, 'id'>;
//
// type ObjectContainingFileFormGroupContent = {
//   id: FormControl<IObjectContainingFile['id'] | NewObjectContainingFile['id']>;
//   file: FormControl<IObjectContainingFile['file']>;
//   fileContentType: FormControl<IObjectContainingFile['fileContentType']>;
//   candidateDocs: FormControl<IObjectContainingFile['candidateDocs']>;
//   noteDocs: FormControl<IObjectContainingFile['noteDocs']>;
//   emailDocs: FormControl<IObjectContainingFile['emailDocs']>;
//   candidateAdditionalInfos: FormControl<IObjectContainingFile['candidateAdditionalInfos']>;
//   note: FormControl<IObjectContainingFile['note']>;
//   email: FormControl<IObjectContainingFile['email']>;
// };
//
// export type ObjectContainingFileFormGroup = FormGroup<ObjectContainingFileFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class ObjectContainingFileFormService {
//   createObjectContainingFileFormGroup(
//     objectContainingFile: ObjectContainingFileFormGroupInput = { id: null },
//   ): ObjectContainingFileFormGroup {
//     const objectContainingFileRawValue = {
//       ...this.getFormDefaults(),
//       ...objectContainingFile,
//     };
//     return new FormGroup<ObjectContainingFileFormGroupContent>({
//       id: new FormControl(
//         { value: objectContainingFileRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       file: new FormControl(objectContainingFileRawValue.file),
//       fileContentType: new FormControl(objectContainingFileRawValue.fileContentType),
//       candidateDocs: new FormControl(objectContainingFileRawValue.candidateDocs),
//       noteDocs: new FormControl(objectContainingFileRawValue.noteDocs),
//       emailDocs: new FormControl(objectContainingFileRawValue.emailDocs),
//       candidateAdditionalInfos: new FormControl(objectContainingFileRawValue.candidateAdditionalInfos),
//       note: new FormControl(objectContainingFileRawValue.note),
//       email: new FormControl(objectContainingFileRawValue.email),
//     });
//   }
//
//   getObjectContainingFile(form: ObjectContainingFileFormGroup): IObjectContainingFile | NewObjectContainingFile {
//     return form.getRawValue() as IObjectContainingFile | NewObjectContainingFile;
//   }
//
//   resetForm(form: ObjectContainingFileFormGroup, objectContainingFile: ObjectContainingFileFormGroupInput): void {
//     const objectContainingFileRawValue = { ...this.getFormDefaults(), ...objectContainingFile };
//     form.reset(
//       {
//         ...objectContainingFileRawValue,
//         id: { value: objectContainingFileRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): ObjectContainingFileFormDefaults {
//     return {
//       id: null,
//     };
//   }
// }
