import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';
import {
    ManHoursService,
    ManHoursPopupService,
    ManHoursComponent,
    ManHoursDetailComponent,
    ManHoursDialogComponent,
    ManHoursPopupComponent,
    ManHoursDeletePopupComponent,
    ManHoursDeleteDialogComponent,
    manHoursRoute,
    manHoursPopupRoute,
} from './';

const ENTITY_STATES = [
    ...manHoursRoute,
    ...manHoursPopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ManHoursComponent,
        ManHoursDetailComponent,
        ManHoursDialogComponent,
        ManHoursDeleteDialogComponent,
        ManHoursPopupComponent,
        ManHoursDeletePopupComponent,
    ],
    entryComponents: [
        ManHoursComponent,
        ManHoursDialogComponent,
        ManHoursPopupComponent,
        ManHoursDeleteDialogComponent,
        ManHoursDeletePopupComponent,
    ],
    providers: [
        ManHoursService,
        ManHoursPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitManHoursModule {}
