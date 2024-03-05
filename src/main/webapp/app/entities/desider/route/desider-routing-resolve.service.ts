import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDesider } from '../desider.model';
import { DesiderService } from '../service/desider.service';

export const desiderResolve = (route: ActivatedRouteSnapshot): Observable<null | IDesider> => {
  const id = route.params['id'];
  if (id) {
    return inject(DesiderService)
      .find(id)
      .pipe(
        mergeMap((desider: HttpResponse<IDesider>) => {
          if (desider.body) {
            return of(desider.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default desiderResolve;
