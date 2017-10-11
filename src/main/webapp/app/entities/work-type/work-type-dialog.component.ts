import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkType } from './work-type.model';
import { WorkTypePopupService } from './work-type-popup.service';
import { WorkTypeService } from './work-type.service';

@Component({
    selector: 'jhi-work-type-dialog',
    templateUrl: './work-type-dialog.component.html'
})
export class WorkTypeDialogComponent implements OnInit {

    workType: WorkType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private workTypeService: WorkTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.workType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workTypeService.update(this.workType));
        } else {
            this.subscribeToSaveResponse(
                this.workTypeService.create(this.workType));
        }
    }

    private subscribeToSaveResponse(result: Observable<WorkType>) {
        result.subscribe((res: WorkType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WorkType) {
        this.eventManager.broadcast({ name: 'workTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-work-type-popup',
    template: ''
})
export class WorkTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workTypePopupService: WorkTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.workTypePopupService
                    .open(WorkTypeDialogComponent as Component, params['id']);
            } else {
                this.workTypePopupService
                    .open(WorkTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
