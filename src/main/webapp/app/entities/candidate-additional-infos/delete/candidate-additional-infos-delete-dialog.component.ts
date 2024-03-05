import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICandidateAdditionalInfos } from '../candidate-additional-infos.model';
import { CandidateAdditionalInfosService } from '../service/candidate-additional-infos.service';

@Component({
  standalone: true,
  templateUrl: './candidate-additional-infos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CandidateAdditionalInfosDeleteDialogComponent {
  candidateAdditionalInfos?: ICandidateAdditionalInfos;

  constructor(
    protected candidateAdditionalInfosService: CandidateAdditionalInfosService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.candidateAdditionalInfosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
