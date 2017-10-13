import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Mechanic } from './mechanic.model';
import { MechanicService } from './mechanic.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

//ThuyetLV
import { TreeModule, TREE_ACTIONS, KEYS, IActionMapping, ITreeOptions, TreeNode } from 'angular-tree-component';
import { FileSelectDirective } from 'ng2-file-upload';
//Tree company
import { Company } from '../company/company.model';
import { CompanyService } from '../company/company.service';

@Component({
    selector: 'jhi-mechanic',
    templateUrl: './mechanic.component.html'
})
export class MechanicComponent implements OnInit, OnDestroy {

currentAccount: any;
    mechanics: Mechanic[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private companyService: CompanyService,
        private mechanicService: MechanicService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig
    ) {
        console.log("---MechanicComponent constructor------");
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.mechanicService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/mechanic'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/mechanic', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadTreeCompany();
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMechanics();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Mechanic) {
        return item.id;
    }
    registerChangeInMechanics() {
        this.eventSubscriber = this.eventManager.subscribe('mechanicListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        console.log(data);
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.mechanics = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    
    //
    //ThuyetLV
    treeData: any;
    loadTreeCompany() {
        console.log("-------loadTreeCompany----");
        this.companyService.getTree().subscribe(
            (res: ResponseWrapper) => { this.treeData = res.json; },
            (res: ResponseWrapper) => { console.error(res.json); }
        );
    }
    
    onDblclick(item){
        console.log("--------onDblclick: " + item.id);
        this.mechanicService.getMechanics(item.id,{
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMechanics();
    }
    
    interval: any;
    options = {
        getChildren: (node:TreeNode) => {
            console.log(node);
            return this.companyService.getTreePromise(node.id);
        }
    }
}
