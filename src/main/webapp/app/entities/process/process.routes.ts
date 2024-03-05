import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProcessComponent } from './list/process.component';
import { ProcessDetailComponent } from './detail/process-detail.component';
import { ProcessUpdateComponent } from './update/process-update.component';
import ProcessResolve from './route/process-routing-resolve.service';

const processRoute: Routes = [
  {
    path: '',
    component: ProcessComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcessDetailComponent,
    resolve: {
      process: ProcessResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcessUpdateComponent,
    resolve: {
      process: ProcessResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcessUpdateComponent,
    resolve: {
      process: ProcessResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default processRoute;
