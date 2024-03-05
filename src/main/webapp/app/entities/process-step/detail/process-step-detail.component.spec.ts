import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProcessStepDetailComponent } from './process-step-detail.component';

describe('ProcessStep Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessStepDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProcessStepDetailComponent,
              resolve: { processStep: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProcessStepDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load processStep on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProcessStepDetailComponent);

      // THEN
      expect(instance.processStep).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
