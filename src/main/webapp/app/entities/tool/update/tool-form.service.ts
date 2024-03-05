import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITool, NewTool } from '../tool.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITool for edit and NewToolFormGroupInput for create.
 */
type ToolFormGroupInput = ITool | PartialWithRequiredKeyOf<NewTool>;

type ToolFormDefaults = Pick<NewTool, 'id'>;

type ToolFormGroupContent = {
  id: FormControl<ITool['id'] | NewTool['id']>;
  tool: FormControl<ITool['tool']>;
  toolExperience: FormControl<ITool['toolExperience']>;
  experience: FormControl<ITool['experience']>;
};

export type ToolFormGroup = FormGroup<ToolFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ToolFormService {
  createToolFormGroup(tool: ToolFormGroupInput = { id: null }): ToolFormGroup {
    const toolRawValue = {
      ...this.getFormDefaults(),
      ...tool,
    };
    return new FormGroup<ToolFormGroupContent>({
      id: new FormControl(
        { value: toolRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tool: new FormControl(toolRawValue.tool),
      toolExperience: new FormControl(toolRawValue.toolExperience),
      experience: new FormControl(toolRawValue.experience),
    });
  }

  getTool(form: ToolFormGroup): ITool | NewTool {
    return form.getRawValue() as ITool | NewTool;
  }

  resetForm(form: ToolFormGroup, tool: ToolFormGroupInput): void {
    const toolRawValue = { ...this.getFormDefaults(), ...tool };
    form.reset(
      {
        ...toolRawValue,
        id: { value: toolRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ToolFormDefaults {
    return {
      id: null,
    };
  }
}
