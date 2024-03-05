import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ToolDetailComponent } from './tool-detail.component';

describe('Tool Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ToolDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ToolDetailComponent,
              resolve: { tool: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ToolDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load tool on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ToolDetailComponent);

      // THEN
      expect(instance.tool).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
