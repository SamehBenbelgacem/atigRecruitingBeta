import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISubEmail } from '../sub-email.model';
import { SubEmailService } from '../service/sub-email.service';

@Component({
  standalone: true,
  templateUrl: './sub-email-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SubEmailDeleteDialogComponent {
  subEmail?: ISubEmail;

  constructor(
    protected subEmailService: SubEmailService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subEmailService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
