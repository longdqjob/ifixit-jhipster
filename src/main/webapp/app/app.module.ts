import './vendor.ts';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { IfixitSharedModule, UserRouteAccessService } from './shared';
import { IfixitHomeModule } from './home/home.module';
import { IfixitAdminModule } from './admin/admin.module';
import { IfixitAccountModule } from './account/account.module';
import { IfixitEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
// jhipster-needle-angular-add-module-import JHipster will add new module here

//ThuyetLV
import { TreeModule, TREE_ACTIONS, KEYS, IActionMapping, ITreeOptions } from 'angular-tree-component';
import { FileUploadModule } from 'ng2-file-upload';


import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        IfixitSharedModule,
        IfixitHomeModule,
        IfixitAdminModule,
        IfixitAccountModule,
        IfixitEntityModule, 
        // jhipster-needle-angular-add-module JHipster will add new module here
        TreeModule,
        FileUploadModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class IfixitAppModule {}
