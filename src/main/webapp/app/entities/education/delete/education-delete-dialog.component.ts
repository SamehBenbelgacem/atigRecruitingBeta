import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEducation } from '../education.model';
import { EducationService } from '../service/education.service';

@Component({
  standalone: true,
  templateUrl: './education-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EducationDeleteDialogComponent {
  education?: IEducation;

  constructor(
    protected educationService: EducationService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.educationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
