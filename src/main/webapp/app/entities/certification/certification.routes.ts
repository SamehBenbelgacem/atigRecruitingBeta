import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CertificationComponent } from './list/certification.component';
import { CertificationDetailComponent } from './detail/certification-detail.component';
import { CertificationUpdateComponent } from './update/certification-update.component';
import CertificationResolve from './route/certification-routing-resolve.service';

const certificationRoute: Routes = [
  {
    path: '',
    component: CertificationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CertificationDetailComponent,
    resolve: {
      certification: CertificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CertificationUpdateComponent,
    resolve: {
      certification: CertificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CertificationUpdateComponent,
    resolve: {
      certification: CertificationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default certificationRoute;
