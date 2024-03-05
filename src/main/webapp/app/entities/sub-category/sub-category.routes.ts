import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SubCategoryComponent } from './list/sub-category.component';
import { SubCategoryDetailComponent } from './detail/sub-category-detail.component';
import { SubCategoryUpdateComponent } from './update/sub-category-update.component';
import SubCategoryResolve from './route/sub-category-routing-resolve.service';

const subCategoryRoute: Routes = [
  {
    path: '',
    component: SubCategoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubCategoryDetailComponent,
    resolve: {
      subCategory: SubCategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubCategoryUpdateComponent,
    resolve: {
      subCategory: SubCategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubCategoryUpdateComponent,
    resolve: {
      subCategory: SubCategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subCategoryRoute;
