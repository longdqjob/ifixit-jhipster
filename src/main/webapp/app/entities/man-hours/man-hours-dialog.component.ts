import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ManHours } from './man-hours.model';
import { ManHoursPopupService } from './man-hours-popup.service';
import { ManHoursService } from './man-hours.service';
import { WorkOrder, WorkOrderService } from '../work-order';
import { GroupEngineer, GroupEngineerService } from '../group-engineer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-man-hours-dialog',
    templateUrl: './man-hours-dialog.component.html'
})
export class ManHoursDialogComponent implements OnInit {

    manHours: ManHours;
    isSaving: boolean;

    workorders: WorkOrder[];

    groupengineers: GroupEngineer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private manHoursService: ManHoursService,
        private workOrderService: WorkOrderService,
        private groupEngineerService: GroupEngineerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.workOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.workorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.groupEngineerService.query()
            .subscribe((res: ResponseWrapper) => { this.groupengineers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.manHours.id !== undefined) {
            this.subscribeToSaveResponse(
                this.manHoursService.update(this.manHours));
        } else {
            this.subscribeToSaveResponse(
                this.manHoursService.create(this.manHours));
        }
    }

    private subscribeToSaveResponse(result: Observable<ManHours>) {
        result.subscribe((res: ManHours) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ManHours) {
        this.eventManager.broadcast({ name: 'manHoursListModification', content: 'OK'});
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

    trackGroupEngineerById(index: number, item: GroupEngineer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-man-hours-popup',
    template: ''
})
export class ManHoursPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private manHoursPopupService: ManHoursPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.manHoursPopupService
                    .open(ManHoursDialogComponent as Component, params['id']);
            } else {
                this.manHoursPopupService
                    .open(ManHoursDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
