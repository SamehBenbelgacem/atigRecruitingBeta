import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEmailcredentials } from '../emailcredentials.model';

@Component({
  standalone: true,
  selector: 'jhi-emailcredentials-detail',
  templateUrl: './emailcredentials-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EmailcredentialsDetailComponent {
  @Input() emailcredentials: IEmailcredentials | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
