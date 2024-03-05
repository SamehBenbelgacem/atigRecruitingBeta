import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ObjectContainingFileComponent } from './list/object-containing-file.component';
import { ObjectContainingFileDetailComponent } from './detail/object-containing-file-detail.component';
import { ObjectContainingFileUpdateComponent } from './update/object-containing-file-update.component';
import ObjectContainingFileResolve from './route/object-containing-file-routing-resolve.service';

const objectContainingFileRoute: Routes = [
  {
    path: '',
    component: ObjectContainingFileComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObjectContainingFileDetailComponent,
    resolve: {
      objectContainingFile: ObjectContainingFileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObjectContainingFileUpdateComponent,
    resolve: {
      objectContainingFile: ObjectContainingFileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObjectContainingFileUpdateComponent,
    resolve: {
      objectContainingFile: ObjectContainingFileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default objectContainingFileRoute;
