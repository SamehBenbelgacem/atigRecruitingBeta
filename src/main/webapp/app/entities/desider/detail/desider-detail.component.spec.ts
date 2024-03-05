import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DesiderDetailComponent } from './desider-detail.component';

describe('Desider Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesiderDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DesiderDetailComponent,
              resolve: { desider: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DesiderDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load desider on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DesiderDetailComponent);

      // THEN
      expect(instance.desider).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
