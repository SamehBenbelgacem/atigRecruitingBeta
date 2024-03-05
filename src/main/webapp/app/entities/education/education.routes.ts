import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EducationComponent } from './list/education.component';
import { EducationDetailComponent } from './detail/education-detail.component';
import { EducationUpdateComponent } from './update/education-update.component';
import EducationResolve from './route/education-routing-resolve.service';

const educationRoute: Routes = [
  {
    path: '',
    component: EducationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EducationDetailComponent,
    resolve: {
      education: EducationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EducationUpdateComponent,
    resolve: {
      education: EducationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EducationUpdateComponent,
    resolve: {
      education: EducationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default educationRoute;
