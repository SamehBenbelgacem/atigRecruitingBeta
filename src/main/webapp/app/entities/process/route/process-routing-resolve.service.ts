import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProcess } from '../process.model';
import { ProcessService } from '../service/process.service';

export const processResolve = (route: ActivatedRouteSnapshot): Observable<null | IProcess> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProcessService)
      .find(id)
      .pipe(
        mergeMap((process: HttpResponse<IProcess>) => {
          if (process.body) {
            return of(process.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default processResolve;
