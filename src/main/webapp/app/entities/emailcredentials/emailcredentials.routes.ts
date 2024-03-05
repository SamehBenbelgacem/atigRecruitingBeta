import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EmailcredentialsComponent } from './list/emailcredentials.component';
import { EmailcredentialsDetailComponent } from './detail/emailcredentials-detail.component';
import { EmailcredentialsUpdateComponent } from './update/emailcredentials-update.component';
import EmailcredentialsResolve from './route/emailcredentials-routing-resolve.service';

const emailcredentialsRoute: Routes = [
  {
    path: '',
    component: EmailcredentialsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmailcredentialsDetailComponent,
    resolve: {
      emailcredentials: EmailcredentialsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmailcredentialsUpdateComponent,
    resolve: {
      emailcredentials: EmailcredentialsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmailcredentialsUpdateComponent,
    resolve: {
      emailcredentials: EmailcredentialsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default emailcredentialsRoute;
