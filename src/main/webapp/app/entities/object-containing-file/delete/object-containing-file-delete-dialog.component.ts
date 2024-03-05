import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IObjectContainingFile } from '../object-containing-file.model';
import { ObjectContainingFileService } from '../service/object-containing-file.service';

@Component({
  standalone: true,
  templateUrl: './object-containing-file-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ObjectContainingFileDeleteDialogComponent {
  objectContainingFile?: IObjectContainingFile;

  constructor(
    protected objectContainingFileService: ObjectContainingFileService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.objectContainingFileService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
