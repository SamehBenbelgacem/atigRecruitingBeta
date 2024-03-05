import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICertification } from '../certification.model';
import { CertificationService } from '../service/certification.service';

export const certificationResolve = (route: ActivatedRouteSnapshot): Observable<null | ICertification> => {
  const id = route.params['id'];
  if (id) {
    return inject(CertificationService)
      .find(id)
      .pipe(
        mergeMap((certification: HttpResponse<ICertification>) => {
          if (certification.body) {
            return of(certification.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default certificationResolve;
