import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';
import {
    WorkOrderService,
    WorkOrderPopupService,
    WorkOrderComponent,
    WorkOrderDetailComponent,
    WorkOrderDialogComponent,
    WorkOrderPopupComponent,
    WorkOrderDeletePopupComponent,
    WorkOrderDeleteDialogComponent,
    workOrderRoute,
    workOrderPopupRoute,
    WorkOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...workOrderRoute,
    ...workOrderPopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WorkOrderComponent,
        WorkOrderDetailComponent,
        WorkOrderDialogComponent,
        WorkOrderDeleteDialogComponent,
        WorkOrderPopupComponent,
        WorkOrderDeletePopupComponent,
    ],
    entryComponents: [
        WorkOrderComponent,
        WorkOrderDialogComponent,
        WorkOrderPopupComponent,
        WorkOrderDeleteDialogComponent,
        WorkOrderDeletePopupComponent,
    ],
    providers: [
        WorkOrderService,
        WorkOrderPopupService,
        WorkOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitWorkOrderModule {}
