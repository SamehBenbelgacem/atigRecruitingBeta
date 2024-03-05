import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICertification } from '../certification.model';
import { CertificationService } from '../service/certification.service';

@Component({
  standalone: true,
  templateUrl: './certification-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CertificationDeleteDialogComponent {
  certification?: ICertification;

  constructor(
    protected certificationService: CertificationService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.certificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
