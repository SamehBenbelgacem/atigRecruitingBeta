import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IObjectContainingFile } from '../object-containing-file.model';
import { ObjectContainingFileService } from '../service/object-containing-file.service';

export const objectContainingFileResolve = (route: ActivatedRouteSnapshot): Observable<null | IObjectContainingFile> => {
  const id = route.params['id'];
  if (id) {
    return inject(ObjectContainingFileService)
      .find(id)
      .pipe(
        mergeMap((objectContainingFile: HttpResponse<IObjectContainingFile>) => {
          if (objectContainingFile.body) {
            return of(objectContainingFile.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default objectContainingFileResolve;
