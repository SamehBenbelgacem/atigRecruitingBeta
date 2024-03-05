import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SubCategoryDetailComponent } from './sub-category-detail.component';

describe('SubCategory Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubCategoryDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SubCategoryDetailComponent,
              resolve: { subCategory: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SubCategoryDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load subCategory on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SubCategoryDetailComponent);

      // THEN
      expect(instance.subCategory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
