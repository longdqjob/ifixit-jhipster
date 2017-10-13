import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Material } from './material.model';
import { MaterialService } from './material.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

//
import { LocalStorageService, SessionStorageService } from 'ng2-webstorage';
import { FileUploader, FileUploaderOptions } from 'ng2-file-upload';

const URL = '/api/post';

@Component({
    selector: 'jhi-material',
    templateUrl: './material.component.html',
     styleUrls: [
        '../../../../../../node_modules/angular-tree-component/dist/angular-tree-component.css'
  ],
})
export class MaterialComponent implements OnInit, OnDestroy {

currentAccount: any;
    materials: Material[];
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
    //ThuyetLV
    token: string;
    uploader: any;
    maxFileSize = 10 * 1024 * 1024;
    allowedMimeType = ['image/png', 'image/gif', 'video/mp4', 'image/jpeg'];

    constructor(
        private materialService: MaterialService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
        private localStorage: LocalStorageService,
        private sessionStorage: SessionStorageService
    ) {
        this.token = this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
        console.log("---token: " + this.token);
    
        this.uploader = new FileUploader({
            url: URL,
            headers: [{name:'Authorization', value:'Bearer ' + this.token}],
            autoUpload: true,
                maxFileSize: this.maxFileSize,
                allowedMimeType: this.allowedMimeType,
    //            filters: [{
    //                    name: 'extension',
    //                    fn: (item: any): boolean => {
    //                        console.log(item.name);
    //                        const fileExtension = item.name.slice(item.name.lastIndexOf('.') + 1).toLowerCase();
    //                        return fileExtension === 'csv' ;
    //                    }
    //                }
    //            ]
        });
    
        this.uploader.onCompleteItem = (item:any, response:any, status:any, headers:any) => {
            console.log(response);
            console.log(response.map.fileName);
            console.log("ImageUpload:uploaded:", item, status);
        };
        console.log("-------itemsPerPage------");
    
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.materialService.getAll({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
//        this.materialService.query({
//            page: this.page - 1,
//            size: this.itemsPerPage,
//            sort: this.sort()}).subscribe(
//            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
//            (res: ResponseWrapper) => this.onError(res.json)
//        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/material'], {queryParams:
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
        this.router.navigate(['/material', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMaterials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Material) {
        return item.id;
    }
    registerChangeInMaterials() {
        this.eventSubscriber = this.eventManager.subscribe('materialListModification', (response) => this.loadAll());
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
        this.materials = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    
    //ThuyetLV
    doubleClick(event,material){
        console.log(event);
        console.log(material);
        //navigation link.
        this.router.navigate(['/', { outlets: { popup: 'material/'+ material.id + '/edit'} }]);
    }
    
    
  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;
 
  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }
 
  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
  }
  
  onFileSelected () {
    this.uploader.uploadAll();
  }
}
