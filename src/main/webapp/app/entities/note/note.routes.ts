import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { NoteComponent } from './list/note.component';
import { NoteDetailComponent } from './detail/note-detail.component';
import { NoteUpdateComponent } from './update/note-update.component';
import NoteResolve from './route/note-routing-resolve.service';

const noteRoute: Routes = [
  {
    path: '',
    component: NoteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NoteDetailComponent,
    resolve: {
      note: NoteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NoteUpdateComponent,
    resolve: {
      note: NoteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NoteUpdateComponent,
    resolve: {
      note: NoteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default noteRoute;
