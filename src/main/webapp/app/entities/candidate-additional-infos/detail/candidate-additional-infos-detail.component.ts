import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICandidateAdditionalInfos } from '../candidate-additional-infos.model';

@Component({
  standalone: true,
  selector: 'jhi-candidate-additional-infos-detail',
  templateUrl: './candidate-additional-infos-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CandidateAdditionalInfosDetailComponent {
  @Input() candidateAdditionalInfos: ICandidateAdditionalInfos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
