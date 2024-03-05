import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubEmail } from '../sub-email.model';
import { SubEmailService } from '../service/sub-email.service';

export const subEmailResolve = (route: ActivatedRouteSnapshot): Observable<null | ISubEmail> => {
  const id = route.params['id'];
  if (id) {
    return inject(SubEmailService)
      .find(id)
      .pipe(
        mergeMap((subEmail: HttpResponse<ISubEmail>) => {
          if (subEmail.body) {
            return of(subEmail.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default subEmailResolve;
