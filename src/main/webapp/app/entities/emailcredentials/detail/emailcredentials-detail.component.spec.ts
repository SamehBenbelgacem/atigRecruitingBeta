import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmailcredentialsDetailComponent } from './emailcredentials-detail.component';

describe('Emailcredentials Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmailcredentialsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EmailcredentialsDetailComponent,
              resolve: { emailcredentials: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmailcredentialsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load emailcredentials on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmailcredentialsDetailComponent);

      // THEN
      expect(instance.emailcredentials).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
