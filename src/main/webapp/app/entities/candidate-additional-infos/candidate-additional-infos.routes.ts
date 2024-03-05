import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CandidateAdditionalInfosComponent } from './list/candidate-additional-infos.component';
import { CandidateAdditionalInfosDetailComponent } from './detail/candidate-additional-infos-detail.component';
import { CandidateAdditionalInfosUpdateComponent } from './update/candidate-additional-infos-update.component';
import CandidateAdditionalInfosResolve from './route/candidate-additional-infos-routing-resolve.service';

const candidateAdditionalInfosRoute: Routes = [
  {
    path: '',
    component: CandidateAdditionalInfosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CandidateAdditionalInfosDetailComponent,
    resolve: {
      candidateAdditionalInfos: CandidateAdditionalInfosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CandidateAdditionalInfosUpdateComponent,
    resolve: {
      candidateAdditionalInfos: CandidateAdditionalInfosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CandidateAdditionalInfosUpdateComponent,
    resolve: {
      candidateAdditionalInfos: CandidateAdditionalInfosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default candidateAdditionalInfosRoute;
