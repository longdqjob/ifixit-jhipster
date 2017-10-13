import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';

import { TreeModule, TREE_ACTIONS, KEYS, IActionMapping, ITreeOptions, TreeNode } from 'angular-tree-component';

import {
    CompanyService,
    CompanyPopupService,
    CompanyComponent,
    CompanyDetailComponent,
    CompanyDialogComponent,
    CompanyPopupComponent,
    CompanyDeletePopupComponent,
    CompanyDeleteDialogComponent,
    companyRoute,
    companyPopupRoute,
    CompanyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companyRoute,
    ...companyPopupRoute,
];

@NgModule({
    imports: [
        TreeModule,
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyComponent,
        CompanyDetailComponent,
        CompanyDialogComponent,
        CompanyDeleteDialogComponent,
        CompanyPopupComponent,
        CompanyDeletePopupComponent,
    ],
    entryComponents: [
        CompanyComponent,
        CompanyDialogComponent,
        CompanyPopupComponent,
        CompanyDeleteDialogComponent,
        CompanyDeletePopupComponent,
    ],
    providers: [
        CompanyService,
        CompanyPopupService,
        CompanyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitCompanyModule {}
