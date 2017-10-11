import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GroupEngineerComponent } from './group-engineer.component';
import { GroupEngineerDetailComponent } from './group-engineer-detail.component';
import { GroupEngineerPopupComponent } from './group-engineer-dialog.component';
import { GroupEngineerDeletePopupComponent } from './group-engineer-delete-dialog.component';

@Injectable()
export class GroupEngineerResolvePagingParams implements Resolve<any> {

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

export const groupEngineerRoute: Routes = [
    {
        path: 'group-engineer',
        component: GroupEngineerComponent,
        resolve: {
            'pagingParams': GroupEngineerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.groupEngineer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'group-engineer/:id',
        component: GroupEngineerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.groupEngineer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupEngineerPopupRoute: Routes = [
    {
        path: 'group-engineer-new',
        component: GroupEngineerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.groupEngineer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'group-engineer/:id/edit',
        component: GroupEngineerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.groupEngineer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'group-engineer/:id/delete',
        component: GroupEngineerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.groupEngineer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
