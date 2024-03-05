import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICandidateAdditionalInfos } from '../candidate-additional-infos.model';
import { CandidateAdditionalInfosService } from '../service/candidate-additional-infos.service';

export const candidateAdditionalInfosResolve = (route: ActivatedRouteSnapshot): Observable<null | ICandidateAdditionalInfos> => {
  const id = route.params['id'];
  if (id) {
    return inject(CandidateAdditionalInfosService)
      .find(id)
      .pipe(
        mergeMap((candidateAdditionalInfos: HttpResponse<ICandidateAdditionalInfos>) => {
          if (candidateAdditionalInfos.body) {
            return of(candidateAdditionalInfos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default candidateAdditionalInfosResolve;
