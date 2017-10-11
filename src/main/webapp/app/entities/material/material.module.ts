import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';

//ThuyetLV
import { TreeItemComponent } from '../common/treeItem.component';
import { DropdownTreeviewModule } from 'ng2-dropdown-treeview';

//ThuyetLV
import { FileSelectDirective } from 'ng2-file-upload';

import {
    MaterialService,
    MaterialPopupService,
    MaterialComponent,
    MaterialDetailComponent,
    MaterialDialogComponent,
    MaterialPopupComponent,
    MaterialDeletePopupComponent,
    MaterialDeleteDialogComponent,
    materialRoute,
    materialPopupRoute,
    MaterialResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...materialRoute,
    ...materialPopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DropdownTreeviewModule.forRoot()
    ],
    declarations: [
        MaterialComponent,
        MaterialDetailComponent,
        MaterialDialogComponent,
        MaterialDeleteDialogComponent,
        MaterialPopupComponent,
        MaterialDeletePopupComponent,
        TreeItemComponent,
        FileSelectDirective
    ],
    entryComponents: [
        MaterialComponent,
        MaterialDialogComponent,
        MaterialPopupComponent,
        MaterialDeleteDialogComponent,
        MaterialDeletePopupComponent,
    ],
    providers: [
        MaterialService,
        MaterialPopupService,
        MaterialResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitMaterialModule {}
