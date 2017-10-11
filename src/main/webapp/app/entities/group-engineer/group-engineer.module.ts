import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';
import {
    GroupEngineerService,
    GroupEngineerPopupService,
    GroupEngineerComponent,
    GroupEngineerDetailComponent,
    GroupEngineerDialogComponent,
    GroupEngineerPopupComponent,
    GroupEngineerDeletePopupComponent,
    GroupEngineerDeleteDialogComponent,
    groupEngineerRoute,
    groupEngineerPopupRoute,
    GroupEngineerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...groupEngineerRoute,
    ...groupEngineerPopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GroupEngineerComponent,
        GroupEngineerDetailComponent,
        GroupEngineerDialogComponent,
        GroupEngineerDeleteDialogComponent,
        GroupEngineerPopupComponent,
        GroupEngineerDeletePopupComponent,
    ],
    entryComponents: [
        GroupEngineerComponent,
        GroupEngineerDialogComponent,
        GroupEngineerPopupComponent,
        GroupEngineerDeleteDialogComponent,
        GroupEngineerDeletePopupComponent,
    ],
    providers: [
        GroupEngineerService,
        GroupEngineerPopupService,
        GroupEngineerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitGroupEngineerModule {}
