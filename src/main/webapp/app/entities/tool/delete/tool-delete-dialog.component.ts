import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITool } from '../tool.model';
import { ToolService } from '../service/tool.service';

@Component({
  standalone: true,
  templateUrl: './tool-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ToolDeleteDialogComponent {
  tool?: ITool;

  constructor(
    protected toolService: ToolService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.toolService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
