import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITool } from '../tool.model';
import { ToolService } from '../service/tool.service';

export const toolResolve = (route: ActivatedRouteSnapshot): Observable<null | ITool> => {
  const id = route.params['id'];
  if (id) {
    return inject(ToolService)
      .find(id)
      .pipe(
        mergeMap((tool: HttpResponse<ITool>) => {
          if (tool.body) {
            return of(tool.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default toolResolve;
