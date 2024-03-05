import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DesiderComponent } from './list/desider.component';
import { DesiderDetailComponent } from './detail/desider-detail.component';
import { DesiderUpdateComponent } from './update/desider-update.component';
import DesiderResolve from './route/desider-routing-resolve.service';

const desiderRoute: Routes = [
  {
    path: '',
    component: DesiderComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DesiderDetailComponent,
    resolve: {
      desider: DesiderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DesiderUpdateComponent,
    resolve: {
      desider: DesiderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DesiderUpdateComponent,
    resolve: {
      desider: DesiderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default desiderRoute;
