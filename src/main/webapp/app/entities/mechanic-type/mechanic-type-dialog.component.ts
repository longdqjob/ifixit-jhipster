import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MechanicType } from './mechanic-type.model';
import { MechanicTypePopupService } from './mechanic-type-popup.service';
import { MechanicTypeService } from './mechanic-type.service';

@Component({
    selector: 'jhi-mechanic-type-dialog',
    templateUrl: './mechanic-type-dialog.component.html'
})
export class MechanicTypeDialogComponent implements OnInit {

    mechanicType: MechanicType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mechanicTypeService: MechanicTypeService,
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
        if (this.mechanicType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mechanicTypeService.update(this.mechanicType));
        } else {
            this.subscribeToSaveResponse(
                this.mechanicTypeService.create(this.mechanicType));
        }
    }

    private subscribeToSaveResponse(result: Observable<MechanicType>) {
        result.subscribe((res: MechanicType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MechanicType) {
        this.eventManager.broadcast({ name: 'mechanicTypeListModification', content: 'OK'});
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
    selector: 'jhi-mechanic-type-popup',
    template: ''
})
export class MechanicTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mechanicTypePopupService: MechanicTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mechanicTypePopupService
                    .open(MechanicTypeDialogComponent as Component, params['id']);
            } else {
                this.mechanicTypePopupService
                    .open(MechanicTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
