import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkOrder } from './work-order.model';
import { WorkOrderPopupService } from './work-order-popup.service';
import { WorkOrderService } from './work-order.service';
import { WorkType, WorkTypeService } from '../work-type';
import { GroupEngineer, GroupEngineerService } from '../group-engineer';
import { Mechanic, MechanicService } from '../mechanic';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-work-order-dialog',
    templateUrl: './work-order-dialog.component.html'
})
export class WorkOrderDialogComponent implements OnInit {

    workOrder: WorkOrder;
    isSaving: boolean;

    worktypes: WorkType[];

    groupengineers: GroupEngineer[];

    mechanics: Mechanic[];
    startTimeDp: any;
    endTimeDp: any;
    lastUpdateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private workOrderService: WorkOrderService,
        private workTypeService: WorkTypeService,
        private groupEngineerService: GroupEngineerService,
        private mechanicService: MechanicService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.workTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.worktypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.groupEngineerService.query()
            .subscribe((res: ResponseWrapper) => { this.groupengineers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mechanicService.query()
            .subscribe((res: ResponseWrapper) => { this.mechanics = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.workOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workOrderService.update(this.workOrder));
        } else {
            this.subscribeToSaveResponse(
                this.workOrderService.create(this.workOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<WorkOrder>) {
        result.subscribe((res: WorkOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WorkOrder) {
        this.eventManager.broadcast({ name: 'workOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWorkTypeById(index: number, item: WorkType) {
        return item.id;
    }

    trackGroupEngineerById(index: number, item: GroupEngineer) {
        return item.id;
    }

    trackMechanicById(index: number, item: Mechanic) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-work-order-popup',
    template: ''
})
export class WorkOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workOrderPopupService: WorkOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.workOrderPopupService
                    .open(WorkOrderDialogComponent as Component, params['id']);
            } else {
                this.workOrderPopupService
                    .open(WorkOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
