import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ManHoursComponent } from './man-hours.component';
import { ManHoursDetailComponent } from './man-hours-detail.component';
import { ManHoursPopupComponent } from './man-hours-dialog.component';
import { ManHoursDeletePopupComponent } from './man-hours-delete-dialog.component';

export const manHoursRoute: Routes = [
    {
        path: 'man-hours',
        component: ManHoursComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.manHours.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'man-hours/:id',
        component: ManHoursDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.manHours.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const manHoursPopupRoute: Routes = [
    {
        path: 'man-hours-new',
        component: ManHoursPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.manHours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'man-hours/:id/edit',
        component: ManHoursPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.manHours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'man-hours/:id/delete',
        component: ManHoursDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ifixitApp.manHours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
