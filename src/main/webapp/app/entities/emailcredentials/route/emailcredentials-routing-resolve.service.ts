import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmailcredentials } from '../emailcredentials.model';
import { EmailcredentialsService } from '../service/emailcredentials.service';

export const emailcredentialsResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmailcredentials> => {
  const id = route.params['id'];
  if (id) {
    return inject(EmailcredentialsService)
      .find(id)
      .pipe(
        mergeMap((emailcredentials: HttpResponse<IEmailcredentials>) => {
          if (emailcredentials.body) {
            return of(emailcredentials.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default emailcredentialsResolve;
