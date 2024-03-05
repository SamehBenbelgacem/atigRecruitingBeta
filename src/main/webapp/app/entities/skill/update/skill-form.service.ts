// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import { ISkill, NewSkill } from '../skill.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts ISkill for edit and NewSkillFormGroupInput for create.
//  */
// type SkillFormGroupInput = ISkill | PartialWithRequiredKeyOf<NewSkill>;
//
// type SkillFormDefaults = Pick<NewSkill, 'id'>;
//
// type SkillFormGroupContent = {
//   id: FormControl<ISkill['id'] | NewSkill['id']>;
//   title: FormControl<ISkill['title']>;
//   skillCandidate: FormControl<ISkill['skillCandidate']>;
//   candidate: FormControl<ISkill['candidate']>;
// };
//
// export type SkillFormGroup = FormGroup<SkillFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class SkillFormService {
//   createSkillFormGroup(skill: SkillFormGroupInput = { id: null }): SkillFormGroup {
//     const skillRawValue = {
//       ...this.getFormDefaults(),
//       ...skill,
//     };
//     return new FormGroup<SkillFormGroupContent>({
//       id: new FormControl(
//         { value: skillRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       title: new FormControl(skillRawValue.title),
//       skillCandidate: new FormControl(skillRawValue.skillCandidate),
//       candidate: new FormControl(skillRawValue.candidate),
//     });
//   }
//
//   getSkill(form: SkillFormGroup): ISkill | NewSkill {
//     return form.getRawValue() as ISkill | NewSkill;
//   }
//
//   resetForm(form: SkillFormGroup, skill: SkillFormGroupInput): void {
//     const skillRawValue = { ...this.getFormDefaults(), ...skill };
//     form.reset(
//       {
//         ...skillRawValue,
//         id: { value: skillRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): SkillFormDefaults {
//     return {
//       id: null,
//     };
//   }
// }
