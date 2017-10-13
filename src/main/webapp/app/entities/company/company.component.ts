import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Company } from './company.model';
import { CompanyService } from './company.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

//ThuyetLV
import { TreeModule, TREE_ACTIONS, KEYS, IActionMapping, ITreeOptions, TreeNode } from 'angular-tree-component';

@Component({
    selector: 'jhi-company',
    templateUrl: './company.component.html',
    styleUrls: [
        'company.component.css'
    ]
})
export class CompanyComponent implements OnInit, OnDestroy {

currentAccount: any;
    companies: Company[];
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
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.companyService.query({
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
        this.router.navigate(['/company'], {queryParams:
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
        this.router.navigate(['/company', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        console.log("-------ngOnInit----");
        this.loadTreeCompany();
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Company) {
        return item.id;
    }
    registerChangeInCompanies() {
        this.eventSubscriber = this.eventManager.subscribe('companyListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.companies = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    
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
        console.log("--------onDblclick---------");
        console.log(item);
    }
    
    interval: any;
    options = {
        getChildren: (node:TreeNode) => {
            console.log(node);
            return this.companyService.getTreePromise(node.id);
        }
    }
    nodes = [
        {
          id: 1,
          name: 'root1',
          children: [
            { id: 2, name: 'child1' },
            { id: 3, name: 'child2' }
          ]
        },
        {
          id: 4,
          name: 'root2',
          children: [
            { id: 5, name: 'child2.1' },
            {
              id: 6,
              name: 'child2.2',
              children: [
                { id: 7, name: 'subsub' }
              ]
            }
          ]
        },
        {
          id: 8,
          name: 'asyncRoot',
          hasChildren: true
        }
    ];
  
    nodes2 = [
        {
          title: 'root1',
          className: 'root1Class'
        },
        {
          title: 'root2',
          className: 'root2Class',
          children: [
            { title: 'child1', className: 'child1Class' }
          ]
        }
    ];
}
