import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';

//ThuyetLV
import { TreeModule, TREE_ACTIONS, KEYS, IActionMapping, ITreeOptions, TreeNode } from 'angular-tree-component';

import { FileUploadModule } from "ng2-file-upload";

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
        FileUploadModule,
        TreeModule,
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MechanicComponent,
        MechanicDetailComponent,
        MechanicDialogComponent,
        MechanicDeleteDialogComponent,
        MechanicPopupComponent,
        MechanicDeletePopupComponent,
//        FileSelectDirective
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
