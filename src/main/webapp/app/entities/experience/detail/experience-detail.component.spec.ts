import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ExperienceDetailComponent } from './experience-detail.component';

describe('Experience Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExperienceDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ExperienceDetailComponent,
              resolve: { experience: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ExperienceDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load experience on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ExperienceDetailComponent);

      // THEN
      expect(instance.experience).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
