import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProcessStep } from '../process-step.model';
import { ProcessStepService } from '../service/process-step.service';

export const processStepResolve = (route: ActivatedRouteSnapshot): Observable<null | IProcessStep> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProcessStepService)
      .find(id)
      .pipe(
        mergeMap((processStep: HttpResponse<IProcessStep>) => {
          if (processStep.body) {
            return of(processStep.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default processStepResolve;
