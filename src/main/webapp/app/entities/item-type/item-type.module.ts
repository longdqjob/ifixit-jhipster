import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';
import {
    ItemTypeService,
    ItemTypePopupService,
    ItemTypeComponent,
    ItemTypeDetailComponent,
    ItemTypeDialogComponent,
    ItemTypePopupComponent,
    ItemTypeDeletePopupComponent,
    ItemTypeDeleteDialogComponent,
    itemTypeRoute,
    itemTypePopupRoute,
    ItemTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...itemTypeRoute,
    ...itemTypePopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ItemTypeComponent,
        ItemTypeDetailComponent,
        ItemTypeDialogComponent,
        ItemTypeDeleteDialogComponent,
        ItemTypePopupComponent,
        ItemTypeDeletePopupComponent,
    ],
    entryComponents: [
        ItemTypeComponent,
        ItemTypeDialogComponent,
        ItemTypePopupComponent,
        ItemTypeDeleteDialogComponent,
        ItemTypeDeletePopupComponent,
    ],
    providers: [
        ItemTypeService,
        ItemTypePopupService,
        ItemTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitItemTypeModule {}
