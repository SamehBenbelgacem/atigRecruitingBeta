import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SubEmailComponent } from './list/sub-email.component';
import { SubEmailDetailComponent } from './detail/sub-email-detail.component';
import { SubEmailUpdateComponent } from './update/sub-email-update.component';
import SubEmailResolve from './route/sub-email-routing-resolve.service';

const subEmailRoute: Routes = [
  {
    path: '',
    component: SubEmailComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubEmailDetailComponent,
    resolve: {
      subEmail: SubEmailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubEmailUpdateComponent,
    resolve: {
      subEmail: SubEmailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubEmailUpdateComponent,
    resolve: {
      subEmail: SubEmailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subEmailRoute;
