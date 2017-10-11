import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MechanicTypeComponent } from './mechanic-type.component';
import { MechanicTypeDetailComponent } from './mechanic-type-detail.component';
import { MechanicTypePopupComponent } from './mechanic-type-dialog.component';
import { MechanicTypeDeletePopupComponent } from './mechanic-type-delete-dialog.component';

@Injectable()
export class MechanicTypeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const mechanicTypeRoute: Routes = [
    {
        path: 'mechanic-type',
        component: MechanicTypeComponent,
        resolve: {
            'pagingParams': MechanicTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanicType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mechanic-type/:id',
        component: MechanicTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanicType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mechanicTypePopupRoute: Routes = [
    {
        path: 'mechanic-type-new',
        component: MechanicTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanicType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mechanic-type/:id/edit',
        component: MechanicTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanicType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mechanic-type/:id/delete',
        component: MechanicTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanicType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
