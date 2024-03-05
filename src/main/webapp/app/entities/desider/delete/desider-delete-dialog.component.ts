import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDesider } from '../desider.model';
import { DesiderService } from '../service/desider.service';

@Component({
  standalone: true,
  templateUrl: './desider-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DesiderDeleteDialogComponent {
  desider?: IDesider;

  constructor(
    protected desiderService: DesiderService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.desiderService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
