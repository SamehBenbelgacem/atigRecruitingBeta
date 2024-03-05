import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { OfferDetailComponent } from './offer-detail.component';

describe('Offer Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfferDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: OfferDetailComponent,
              resolve: { offer: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OfferDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load offer on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OfferDetailComponent);

      // THEN
      expect(instance.offer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
