// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import { IProcessStep, NewProcessStep } from '../process-step.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts IProcessStep for edit and NewProcessStepFormGroupInput for create.
//  */
// type ProcessStepFormGroupInput = IProcessStep | PartialWithRequiredKeyOf<NewProcessStep>;
//
// type ProcessStepFormDefaults = Pick<NewProcessStep, 'id'>;
//
// type ProcessStepFormGroupContent = {
//   id: FormControl<IProcessStep['id'] | NewProcessStep['id']>;
//   title: FormControl<IProcessStep['title']>;
//   order: FormControl<IProcessStep['order']>;
//   priority: FormControl<IProcessStep['priority']>;
//   processStepProcess: FormControl<IProcessStep['processStepProcess']>;
//   process: FormControl<IProcessStep['process']>;
// };
//
// export type ProcessStepFormGroup = FormGroup<ProcessStepFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class ProcessStepFormService {
//   createProcessStepFormGroup(processStep: ProcessStepFormGroupInput = { id: null }): ProcessStepFormGroup {
//     const processStepRawValue = {
//       ...this.getFormDefaults(),
//       ...processStep,
//     };
//     return new FormGroup<ProcessStepFormGroupContent>({
//       id: new FormControl(
//         { value: processStepRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       title: new FormControl(processStepRawValue.title),
//       order: new FormControl(processStepRawValue.order),
//       priority: new FormControl(processStepRawValue.priority),
//       processStepProcess: new FormControl(processStepRawValue.processStepProcess),
//       process: new FormControl(processStepRawValue.process),
//     });
//   }
//
//   getProcessStep(form: ProcessStepFormGroup): IProcessStep | NewProcessStep {
//     return form.getRawValue() as IProcessStep | NewProcessStep;
//   }
//
//   resetForm(form: ProcessStepFormGroup, processStep: ProcessStepFormGroupInput): void {
//     const processStepRawValue = { ...this.getFormDefaults(), ...processStep };
//     form.reset(
//       {
//         ...processStepRawValue,
//         id: { value: processStepRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): ProcessStepFormDefaults {
//     return {
//       id: null,
//     };
//   }
// }
