import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ToolComponent } from './list/tool.component';
import { ToolDetailComponent } from './detail/tool-detail.component';
import { ToolUpdateComponent } from './update/tool-update.component';
import ToolResolve from './route/tool-routing-resolve.service';

const toolRoute: Routes = [
  {
    path: '',
    component: ToolComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ToolDetailComponent,
    resolve: {
      tool: ToolResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ToolUpdateComponent,
    resolve: {
      tool: ToolResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ToolUpdateComponent,
    resolve: {
      tool: ToolResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default toolRoute;
