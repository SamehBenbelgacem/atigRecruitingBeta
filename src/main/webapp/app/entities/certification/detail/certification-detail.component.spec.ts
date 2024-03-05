import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CertificationDetailComponent } from './certification-detail.component';

describe('Certification Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CertificationDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CertificationDetailComponent,
              resolve: { certification: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CertificationDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load certification on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CertificationDetailComponent);

      // THEN
      expect(instance.certification).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
