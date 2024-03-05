import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CandidateAdditionalInfosDetailComponent } from './candidate-additional-infos-detail.component';

describe('CandidateAdditionalInfos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CandidateAdditionalInfosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CandidateAdditionalInfosDetailComponent,
              resolve: { candidateAdditionalInfos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CandidateAdditionalInfosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load candidateAdditionalInfos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CandidateAdditionalInfosDetailComponent);

      // THEN
      expect(instance.candidateAdditionalInfos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
