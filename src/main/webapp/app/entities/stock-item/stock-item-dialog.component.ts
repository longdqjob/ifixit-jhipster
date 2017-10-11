import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { StockItem } from './stock-item.model';
import { StockItemPopupService } from './stock-item-popup.service';
import { StockItemService } from './stock-item.service';
import { WorkOrder, WorkOrderService } from '../work-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-stock-item-dialog',
    templateUrl: './stock-item-dialog.component.html'
})
export class StockItemDialogComponent implements OnInit {

    stockItem: StockItem;
    isSaving: boolean;

    workorders: WorkOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private stockItemService: StockItemService,
        private workOrderService: WorkOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.workOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.workorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stockItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stockItemService.update(this.stockItem));
        } else {
            this.subscribeToSaveResponse(
                this.stockItemService.create(this.stockItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<StockItem>) {
        result.subscribe((res: StockItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: StockItem) {
        this.eventManager.broadcast({ name: 'stockItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWorkOrderById(index: number, item: WorkOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-stock-item-popup',
    template: ''
})
export class StockItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stockItemPopupService: StockItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.stockItemPopupService
                    .open(StockItemDialogComponent as Component, params['id']);
            } else {
                this.stockItemPopupService
                    .open(StockItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
