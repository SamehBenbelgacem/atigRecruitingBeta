import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProcessDetailComponent } from './process-detail.component';

describe('Process Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProcessDetailComponent,
              resolve: { process: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProcessDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load process on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProcessDetailComponent);

      // THEN
      expect(instance.process).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
