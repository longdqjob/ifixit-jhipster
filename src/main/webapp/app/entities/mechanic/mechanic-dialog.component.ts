import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mechanic } from './mechanic.model';
import { MechanicPopupService } from './mechanic-popup.service';
import { MechanicService } from './mechanic.service';
import { ItemType, ItemTypeService } from '../item-type';
import { MechanicType, MechanicTypeService } from '../mechanic-type';
import { Company, CompanyService } from '../company';
import { ResponseWrapper } from '../../shared';

//ThuyetLV
import { LocalStorageService, SessionStorageService } from 'ng2-webstorage';
import { FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import { FileSelectDirective } from 'ng2-file-upload';

const URL = '/api/post';

@Component({
    selector: 'jhi-mechanic-dialog',
    templateUrl: './mechanic-dialog.component.html'
})
export class MechanicDialogComponent implements OnInit {

    mechanic: Mechanic;
    isSaving: boolean;

    itemtypes: ItemType[];

    mechanictypes: MechanicType[];

    companies: Company[];
    sinceDp: any;
    
    //ThuyetLV
    imageDefault: string;
    token: string;
    uploader: any;
    mechanicTypeCode: string;
    maxFileSize = 25 * 1024 * 1024;
    allowedMimeType = ['image/png', 'image/gif', 'video/mp4', 'image/jpeg'];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mechanicService: MechanicService,
        private itemTypeService: ItemTypeService,
        private mechanicTypeService: MechanicTypeService,
        private companyService: CompanyService,
        private eventManager: JhiEventManager,
        private localStorage: LocalStorageService,
        private sessionStorage: SessionStorageService
    ) {
    }

    ngOnInit() {
        var mechanicTypeId = null;
        if(this.mechanic.id){
            this.specificationObj = JSON.parse(this.mechanic.specification);
            this.imageDefault= this.mechanic.imgUrl;
            mechanicTypeId = this.mechanic.mechanicTypeId;
        }else{
            this.imageDefault= "/images/no-preview-available.png";
            this.mechanic.imgUrl = "/images/no-preview-available.png";
            this.specificationObj = {"spe01" : "","spe02" : "","spe03" : "","spe04" : "","spe05" : "",
                "spe06" : "","spe07" : "","spe08" : "","spe09" : "","spe10" : ""};
        }
        this.isSaving = false;
        this.itemTypeService.query()
            .subscribe(
                (res: ResponseWrapper) => { 
                    this.itemtypes = res.json;
                }, 
                (res: ResponseWrapper) => this.onError(res.json)
            );
        this.mechanicTypeService.query()
            .subscribe(
                (res: ResponseWrapper) => { 
                    this.mechanictypes = res.json; 
                    //Update thuyetlv
                    if(mechanicTypeId != null){
                        this.onChangeMechanicType(mechanicTypeId);
                    }
                }, 
                (res: ResponseWrapper) => this.onError(res.json)
            );
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
            
        //ThuyetLV Uploader
        this.mechanicTypeCode = "";
        this.token = this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
        console.log("---token: " + this.token);
    
        this.uploader = new FileUploader({
            url: URL,
            headers: [{name:'Authorization', value:'Bearer ' + this.token}],
            autoUpload: true,
        });
    
        this.uploader.onCompleteItem = (item:any, response:any, status:any, headers:any) => {
            try{
                console.log(response);
                console.log("ImageUpload:uploaded:", item, status);
                var obj = JSON.parse(response);
                console.log(obj.map.fileName);
                this.imageDefault= obj.map.url;
                this.mechanic.imgUrl = obj.map.url;
                this.removeAllFile();
                return;
            }catch(c){
                console.error(c);
                return;
            }
        };
        this.uploader.onErrorItem = (item:any, response:any, status:any, headers:any) => {
            try{
                //Handle
                console.log("-----onErrorItem----");
                console.log(response);
            }catch(c){
                console.error(c);
            }
        };
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mechanic.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mechanicService.update(this.mechanic));
        } else {
            this.subscribeToSaveResponse(
                this.mechanicService.create(this.mechanic));
        }
    }

    private subscribeToSaveResponse(result: Observable<Mechanic>) {
        result.subscribe((res: Mechanic) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Mechanic) {
        this.eventManager.broadcast({ name: 'mechanicListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackItemTypeById(index: number, item: ItemType) {
        return item.id;
    }

    trackMechanicTypeById(index: number, item: MechanicType) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
    
    
    //--------------------------------------------------------------------------
    //ThuyetLV
    specificationItemObj: any;
    specificationObj: any;
     
    initMechanicType() {
        this.mechanicTypeCode = "";
        this.specificationItemObj = {"spe01" : "","spe02" : "","spe03" : "","spe04" : "","spe05" : "",
            "spe06" : "","spe07" : "","spe08" : "","spe09" : "","spe10" : ""};
//        this.specificationObj = {"spe01" : "","spe02" : "","spe03" : "","spe04" : "","spe05" : "",
//            "spe06" : "","spe07" : "","spe08" : "","spe09" : "","spe10" : ""};
    }
    
    onChangeMechanicType(newValue):void {
        console.log("---------onChangeItem: " + newValue);
        console.log("---------this.itemtypes: " + this.itemtypes);
        if(this.itemtypes){
            if(newValue){
                this.itemtypes.forEach(function (eachObj) {
                    if(eachObj.id == newValue){
                        console.log(eachObj);
                        this.mechanicTypeCode = eachObj.completeCode;
                        this.specificationItemObj = JSON.parse(eachObj.specification);
                        this.updateCompleteCode();
                    }
                  }, this);            
            }else{
                this.initMechanicType();
                this.updateCompleteCode();
            }
        }else{
            this.initMechanicType();
            this.updateCompleteCode();
        }
    }
    
    updateCompleteCode(){
        if(this.mechanicTypeCode != ""){
            if(this.mechanic && this.mechanic.code){
                this.mechanic.completeCode = (this.mechanicTypeCode + "." + this.mechanic.code).toUpperCase();
            }
        }else{
            this.mechanic.completeCode = this.mechanic.code.toUpperCase();;
        }
    }
    
    onChangeCode(newValue):void {
        this.mechanic.code = newValue;
        this.updateCompleteCode();
    }
    
    
    public hasBaseDropZoneOver:boolean = false;
    public hasAnotherDropZoneOver:boolean = false;

    public fileOverBase(e:any):void {
      this.hasBaseDropZoneOver = e;
    }

    public fileOverAnother(e:any):void {
      this.hasAnotherDropZoneOver = e;
    }

    removeAllFile () {
      for(var i=0; i<this.uploader.queue.length; i++){
            this.uploader.queue[i].remove();
        }
    }
    onFileSelected () {
        //this.removeAllFile();
        this.uploader.uploadAll();
    }
    //--------------------------------------------------------------------------
}

@Component({
    selector: 'jhi-mechanic-popup',
    template: ''
})
export class MechanicPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mechanicPopupService: MechanicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mechanicPopupService
                    .open(MechanicDialogComponent as Component, params['id']);
            } else {
                this.mechanicPopupService
                    .open(MechanicDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
