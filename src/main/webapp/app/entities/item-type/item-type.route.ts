import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ItemTypeComponent } from './item-type.component';
import { ItemTypeDetailComponent } from './item-type-detail.component';
import { ItemTypePopupComponent } from './item-type-dialog.component';
import { ItemTypeDeletePopupComponent } from './item-type-delete-dialog.component';

@Injectable()
export class ItemTypeResolvePagingParams implements Resolve<any> {

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

export const itemTypeRoute: Routes = [
    {
        path: 'item-type',
        component: ItemTypeComponent,
        resolve: {
            'pagingParams': ItemTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.itemType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'item-type/:id',
        component: ItemTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.itemType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemTypePopupRoute: Routes = [
    {
        path: 'item-type-new',
        component: ItemTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.itemType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'item-type/:id/edit',
        component: ItemTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.itemType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'item-type/:id/delete',
        component: ItemTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.itemType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
