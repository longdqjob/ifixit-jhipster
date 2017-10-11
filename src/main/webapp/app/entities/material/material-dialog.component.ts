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

@Component({
    selector: 'jhi-material-dialog',
    templateUrl: './material-dialog.component.html'
})

export class MaterialDialogComponent implements OnInit {

    material: Material;
    isSaving: boolean;
    tabs: {};

    itemtypes: ItemType[];
    
    

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private materialService: MaterialService,
        private itemTypeService: ItemTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        //ThuyetLV
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
}

@Component({
    selector: 'jhi-material-popup',
    template: ''
})
export class MaterialPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

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
