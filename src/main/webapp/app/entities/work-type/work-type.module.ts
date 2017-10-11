import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';
import {
    WorkTypeService,
    WorkTypePopupService,
    WorkTypeComponent,
    WorkTypeDetailComponent,
    WorkTypeDialogComponent,
    WorkTypePopupComponent,
    WorkTypeDeletePopupComponent,
    WorkTypeDeleteDialogComponent,
    workTypeRoute,
    workTypePopupRoute,
    WorkTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...workTypeRoute,
    ...workTypePopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WorkTypeComponent,
        WorkTypeDetailComponent,
        WorkTypeDialogComponent,
        WorkTypeDeleteDialogComponent,
        WorkTypePopupComponent,
        WorkTypeDeletePopupComponent,
    ],
    entryComponents: [
        WorkTypeComponent,
        WorkTypeDialogComponent,
        WorkTypePopupComponent,
        WorkTypeDeleteDialogComponent,
        WorkTypeDeletePopupComponent,
    ],
    providers: [
        WorkTypeService,
        WorkTypePopupService,
        WorkTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitWorkTypeModule {}
