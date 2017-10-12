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
    templateUrl: './material-dialog.component.html'
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
    maxFileSize = 10 * 1024 * 1024;
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
        //ThuyetLV
        this.imageDefault= "/images/no-preview-available.png";
        console.log(this.imageDefault);
        
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
            try{
                console.log(response);
                console.log("ImageUpload:uploaded:", item, status);
                var obj = JSON.parse(response);
                console.log(obj.map.fileName);
                this.imageDefault= "/" + obj.map.fileName;
            }catch(c){
                console.error(c);
            }
        };
        
        console.log("-------tabs------");
        
    this.tabs = [
      {
        title: 'Tab 1 324',
        active: true,
        form: {
          options: {},
          fields: [
            {
              key: 'email',
              type: 'input',
              label: 'Username',
              value: "",
              templateOptions: {
                label: 'Username',
                type: 'email',
                placeholder: 'Email address',
                required: true
              }
            }
          ]
        }
      },
      {
        title: 'Tab 2',
        form: {
          options: {},
          fields: [
            {
              key: 'firstName',
              type: 'input',
              label: 'First Name',
              value: "",
              templateOptions: {
                label: 'First Name',
                required: true
              }
            },
            {
              key: 'lastName',
              type: 'input',
              label: 'Last Name',
              value: "",
              templateOptions: {
                label: 'Last Name',
                required: true
              }
            }
          ] 
        }
      }
    ];
    //End
        this.isSaving = false;
        this.itemTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.itemtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
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
