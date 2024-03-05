import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INote } from '../note.model';
import { NoteService } from '../service/note.service';

export const noteResolve = (route: ActivatedRouteSnapshot): Observable<null | INote> => {
  const id = route.params['id'];
  if (id) {
    return inject(NoteService)
      .find(id)
      .pipe(
        mergeMap((note: HttpResponse<INote>) => {
          if (note.body) {
            return of(note.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default noteResolve;
