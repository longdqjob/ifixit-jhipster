import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mechanic } from './mechanic.model';
import { MechanicPopupService } from './mechanic-popup.service';
import { MechanicService } from './mechanic.service';
import { ItemType, ItemTypeService } from '../item-type';
import { MechanicType, MechanicTypeService } from '../mechanic-type';
import { Company, CompanyService } from '../company';
import { ResponseWrapper } from '../../shared';


@Component({
    selector: 'jhi-mechanic-dialog',
    templateUrl: './mechanic-dialog.component.html'
})
export class MechanicDialogComponent implements OnInit {

    mechanic: Mechanic;
    isSaving: boolean;

    itemtypes: ItemType[];

    mechanictypes: MechanicType[];

    companies: Company[];
    sinceDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mechanicService: MechanicService,
        private itemTypeService: ItemTypeService,
        private mechanicTypeService: MechanicTypeService,
        private companyService: CompanyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.itemTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.itemtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mechanicTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.mechanictypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mechanic.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mechanicService.update(this.mechanic));
        } else {
            this.subscribeToSaveResponse(
                this.mechanicService.create(this.mechanic));
        }
    }

    private subscribeToSaveResponse(result: Observable<Mechanic>) {
        result.subscribe((res: Mechanic) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Mechanic) {
        this.eventManager.broadcast({ name: 'mechanicListModification', content: 'OK'});
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

    trackMechanicTypeById(index: number, item: MechanicType) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mechanic-popup',
    template: ''
})
export class MechanicPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mechanicPopupService: MechanicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mechanicPopupService
                    .open(MechanicDialogComponent as Component, params['id']);
            } else {
                this.mechanicPopupService
                    .open(MechanicDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
