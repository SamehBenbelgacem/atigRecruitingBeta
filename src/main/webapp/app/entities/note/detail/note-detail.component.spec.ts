import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NoteDetailComponent } from './note-detail.component';

describe('Note Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoteDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: NoteDetailComponent,
              resolve: { note: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(NoteDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load note on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', NoteDetailComponent);

      // THEN
      expect(instance.note).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
