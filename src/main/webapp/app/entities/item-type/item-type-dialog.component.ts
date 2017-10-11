import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ItemType } from './item-type.model';
import { ItemTypePopupService } from './item-type-popup.service';
import { ItemTypeService } from './item-type.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-item-type-dialog',
    templateUrl: './item-type-dialog.component.html'
})
export class ItemTypeDialogComponent implements OnInit {

    itemType: ItemType;
    isSaving: boolean;

    itemtypes: ItemType[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private itemTypeService: ItemTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.itemTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.itemtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.itemType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.itemTypeService.update(this.itemType));
        } else {
            this.subscribeToSaveResponse(
                this.itemTypeService.create(this.itemType));
        }
    }

    private subscribeToSaveResponse(result: Observable<ItemType>) {
        result.subscribe((res: ItemType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ItemType) {
        this.eventManager.broadcast({ name: 'itemTypeListModification', content: 'OK'});
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
    selector: 'jhi-item-type-popup',
    template: ''
})
export class ItemTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itemTypePopupService: ItemTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.itemTypePopupService
                    .open(ItemTypeDialogComponent as Component, params['id']);
            } else {
                this.itemTypePopupService
                    .open(ItemTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
