import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEmailcredentials } from '../emailcredentials.model';
import { EmailcredentialsService } from '../service/emailcredentials.service';

@Component({
  standalone: true,
  templateUrl: './emailcredentials-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EmailcredentialsDeleteDialogComponent {
  emailcredentials?: IEmailcredentials;

  constructor(
    protected emailcredentialsService: EmailcredentialsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailcredentialsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
