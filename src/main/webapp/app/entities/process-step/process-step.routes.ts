import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProcessStepComponent } from './list/process-step.component';
import { ProcessStepDetailComponent } from './detail/process-step-detail.component';
import { ProcessStepUpdateComponent } from './update/process-step-update.component';
import ProcessStepResolve from './route/process-step-routing-resolve.service';

const processStepRoute: Routes = [
  {
    path: '',
    component: ProcessStepComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcessStepDetailComponent,
    resolve: {
      processStep: ProcessStepResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcessStepUpdateComponent,
    resolve: {
      processStep: ProcessStepResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcessStepUpdateComponent,
    resolve: {
      processStep: ProcessStepResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default processStepRoute;
