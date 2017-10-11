import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GroupEngineer } from './group-engineer.model';
import { GroupEngineerPopupService } from './group-engineer-popup.service';
import { GroupEngineerService } from './group-engineer.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-group-engineer-dialog',
    templateUrl: './group-engineer-dialog.component.html'
})
export class GroupEngineerDialogComponent implements OnInit {

    groupEngineer: GroupEngineer;
    isSaving: boolean;

    groupengineers: GroupEngineer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private groupEngineerService: GroupEngineerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.groupEngineerService.query()
            .subscribe((res: ResponseWrapper) => { this.groupengineers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.groupEngineer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.groupEngineerService.update(this.groupEngineer));
        } else {
            this.subscribeToSaveResponse(
                this.groupEngineerService.create(this.groupEngineer));
        }
    }

    private subscribeToSaveResponse(result: Observable<GroupEngineer>) {
        result.subscribe((res: GroupEngineer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GroupEngineer) {
        this.eventManager.broadcast({ name: 'groupEngineerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGroupEngineerById(index: number, item: GroupEngineer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-group-engineer-popup',
    template: ''
})
export class GroupEngineerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groupEngineerPopupService: GroupEngineerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.groupEngineerPopupService
                    .open(GroupEngineerDialogComponent as Component, params['id']);
            } else {
                this.groupEngineerPopupService
                    .open(GroupEngineerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
