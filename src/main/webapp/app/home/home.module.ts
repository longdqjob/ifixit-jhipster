import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';

import { TreeModule, TREE_ACTIONS, KEYS, IActionMapping, ITreeOptions, TreeNode } from 'angular-tree-component';

@NgModule({
    imports: [
        TreeModule,
        IfixitSharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitHomeModule {}
