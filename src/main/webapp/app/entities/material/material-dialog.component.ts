import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Material } from './material.model';
import { MaterialPopupService } from './material-popup.service';
import { MaterialService } from './material.service';
import { ItemType, ItemTypeService } from '../item-type';
import { ResponseWrapper } from '../../shared';

//
import { LocalStorageService, SessionStorageService } from 'ng2-webstorage';
import { FileUploader, FileUploaderOptions } from 'ng2-file-upload';

const URL = '/api/post';


@Component({
    selector: 'jhi-material-dialog',
    templateUrl: './material-dialog.component.html',
})

export class MaterialDialogComponent implements OnInit {

    material: Material;
    isSaving: boolean;
    tabs: {};

    itemtypes: ItemType[];
    
    //ThuyetLV
    imageDefault: string;
    token: string;
    uploader: any;
    itemCode: string;
    maxFileSize = 25 * 1024 * 1024;
    allowedMimeType = ['image/png', 'image/gif', 'video/mp4', 'image/jpeg'];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private materialService: MaterialService,
        private itemTypeService: ItemTypeService,
        private eventManager: JhiEventManager,
        private localStorage: LocalStorageService,
        private sessionStorage: SessionStorageService
    ) {
    }

    ngOnInit() {
        var itemTypeId = null;
        if(this.material.id){
            this.specificationObj = JSON.parse(this.material.specification);
            this.imageDefault= this.material.imgUrl;
            itemTypeId = this.material.itemTypeId;
        }else{
            this.imageDefault= "/images/no-preview-available.png";
            this.specificationObj = {"spe01" : "","spe02" : "","spe03" : "","spe04" : "","spe05" : "",
                "spe06" : "","spe07" : "","spe08" : "","spe09" : "","spe10" : ""};
        }
        this.isSaving = false;
        this.itemTypeService.query()
            .subscribe(
                (res: ResponseWrapper) => { 
                    this.itemtypes = res.json;
                    //Update thuyetlv
                    if(itemTypeId != null){
                        this.onChangeItem(itemTypeId);
                    }
                }, 
                (res: ResponseWrapper) => this.onError(res.json)
            );
            
        
        //
        //ThuyetLV
        this.itemCode = "";        
            
        
        
        this.token = this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
        console.log("---token: " + this.token);
    
        this.uploader = new FileUploader({
            url: URL,
            headers: [{name:'Authorization', value:'Bearer ' + this.token}],
            autoUpload: true,
//            maxFileSize: this.maxFileSize,
//            allowedMimeType: this.allowedMimeType,
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
            try{
                console.log(response);
                console.log("ImageUpload:uploaded:", item, status);
                var obj = JSON.parse(response);
                console.log(obj.map.fileName);
                this.imageDefault= obj.map.url;
                this.material.imgUrl = obj.map.url;
                
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
        console.log(this.specificationObj);
        this.material.specification = JSON.stringify(this.specificationObj);
        this.isSaving = true;
        if (this.material.id !== undefined) {
            this.subscribeToSaveResponse(
                this.materialService.update(this.material));
        } else {
            this.subscribeToSaveResponse(
                this.materialService.create(this.material));
        }
    }

    private subscribeToSaveResponse(result: Observable<Material>) {
        result.subscribe((res: Material) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Material) {
        this.eventManager.broadcast({ name: 'materialListModification', content: 'OK'});
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
    
    //ThuyetLV
    specificationItemObj: any;
    specificationObj: any;
    
    identifyItemType(index,item){
        console.log(item);
      //do what ever logic you need to come up with the unique identifier of your item in loop, I will just return the object id.
      return item.id;
    }
     
    initItemType() {
        this.itemCode = "";
        this.specificationItemObj = {"spe01" : "","spe02" : "","spe03" : "","spe04" : "","spe05" : "",
            "spe06" : "","spe07" : "","spe08" : "","spe09" : "","spe10" : ""};
//        this.specificationObj = {"spe01" : "","spe02" : "","spe03" : "","spe04" : "","spe05" : "",
//            "spe06" : "","spe07" : "","spe08" : "","spe09" : "","spe10" : ""};
    }
    
    onChangeItem(newValue):void {
        console.log("---------onChangeItem: " + newValue);
        console.log("---------this.itemtypes: " + this.itemtypes);
        if(this.itemtypes){
            if(newValue){
                this.itemtypes.forEach(function (eachObj) {
                    if(eachObj.id == newValue){
                        console.log(eachObj);
                        this.itemCode = eachObj.completeCode;
                        this.specificationItemObj = JSON.parse(eachObj.specification);
                        this.updateCompleteCode();
                    }
                  }, this);            
            }else{
                this.initItemType();
                this.updateCompleteCode();
            }
        }else{
            this.initItemType();
            this.updateCompleteCode();
        }
    }
    
    updateCompleteCode(){
        if(this.itemCode != ""){
            if(this.material && this.material.code){
                this.material.completeCode = (this.itemCode + "." + this.material.code).toUpperCase();
            }
        }else{
            this.material.completeCode = this.material.code.toUpperCase();;
        }
    }
    
    onChangeCode(newValue):void {
        this.material.code = newValue;
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
}

@Component({
    selector: 'jhi-material-popup',
    template: ''
})
export class MaterialPopupComponent implements OnInit, OnDestroy {

    routeSub: any;
    imageDefault: "/images/no-preview-available.png";

    constructor(
        private route: ActivatedRoute,
        private materialPopupService: MaterialPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.materialPopupService
                    .open(MaterialDialogComponent as Component, params['id']);
            } else {
                this.materialPopupService
                    .open(MaterialDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
