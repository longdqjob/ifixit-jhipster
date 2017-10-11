import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';
import {
    MechanicTypeService,
    MechanicTypePopupService,
    MechanicTypeComponent,
    MechanicTypeDetailComponent,
    MechanicTypeDialogComponent,
    MechanicTypePopupComponent,
    MechanicTypeDeletePopupComponent,
    MechanicTypeDeleteDialogComponent,
    mechanicTypeRoute,
    mechanicTypePopupRoute,
    MechanicTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mechanicTypeRoute,
    ...mechanicTypePopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MechanicTypeComponent,
        MechanicTypeDetailComponent,
        MechanicTypeDialogComponent,
        MechanicTypeDeleteDialogComponent,
        MechanicTypePopupComponent,
        MechanicTypeDeletePopupComponent,
    ],
    entryComponents: [
        MechanicTypeComponent,
        MechanicTypeDialogComponent,
        MechanicTypePopupComponent,
        MechanicTypeDeleteDialogComponent,
        MechanicTypeDeletePopupComponent,
    ],
    providers: [
        MechanicTypeService,
        MechanicTypePopupService,
        MechanicTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitMechanicTypeModule {}
