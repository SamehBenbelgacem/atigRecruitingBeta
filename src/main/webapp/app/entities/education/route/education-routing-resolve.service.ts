import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEducation } from '../education.model';
import { EducationService } from '../service/education.service';

export const educationResolve = (route: ActivatedRouteSnapshot): Observable<null | IEducation> => {
  const id = route.params['id'];
  if (id) {
    return inject(EducationService)
      .find(id)
      .pipe(
        mergeMap((education: HttpResponse<IEducation>) => {
          if (education.body) {
            return of(education.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default educationResolve;
