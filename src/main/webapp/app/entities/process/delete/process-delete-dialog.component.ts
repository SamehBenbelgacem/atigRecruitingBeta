import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProcess } from '../process.model';
import { ProcessService } from '../service/process.service';

@Component({
  standalone: true,
  templateUrl: './process-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProcessDeleteDialogComponent {
  process?: IProcess;

  constructor(
    protected processService: ProcessService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.processService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
