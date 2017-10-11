import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MechanicComponent } from './mechanic.component';
import { MechanicDetailComponent } from './mechanic-detail.component';
import { MechanicPopupComponent } from './mechanic-dialog.component';
import { MechanicDeletePopupComponent } from './mechanic-delete-dialog.component';

@Injectable()
export class MechanicResolvePagingParams implements Resolve<any> {

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

export const mechanicRoute: Routes = [
    {
        path: 'mechanic',
        component: MechanicComponent,
        resolve: {
            'pagingParams': MechanicResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mechanic/:id',
        component: MechanicDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mechanicPopupRoute: Routes = [
    {
        path: 'mechanic-new',
        component: MechanicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mechanic/:id/edit',
        component: MechanicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mechanic/:id/delete',
        component: MechanicDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.mechanic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
