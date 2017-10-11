import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';

import {
    MechanicService,
    MechanicPopupService,
    MechanicComponent,
    MechanicDetailComponent,
    MechanicDialogComponent,
    MechanicPopupComponent,
    MechanicDeletePopupComponent,
    MechanicDeleteDialogComponent,
    mechanicRoute,
    mechanicPopupRoute,
    MechanicResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mechanicRoute,
    ...mechanicPopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MechanicComponent,
        MechanicDetailComponent,
        MechanicDialogComponent,
        MechanicDeleteDialogComponent,
        MechanicPopupComponent,
        MechanicDeletePopupComponent
    ],
    entryComponents: [
        MechanicComponent,
        MechanicDialogComponent,
        MechanicPopupComponent,
        MechanicDeleteDialogComponent,
        MechanicDeletePopupComponent
    ],
    providers: [
        MechanicService,
        MechanicPopupService,
        MechanicResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitMechanicModule {}
