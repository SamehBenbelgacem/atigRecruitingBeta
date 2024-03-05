import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EducationDetailComponent } from './education-detail.component';

describe('Education Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EducationDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EducationDetailComponent,
              resolve: { education: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EducationDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load education on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EducationDetailComponent);

      // THEN
      expect(instance.education).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
