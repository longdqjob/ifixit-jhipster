import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MechanicType } from './mechanic-type.model';
import { MechanicTypePopupService } from './mechanic-type-popup.service';
import { MechanicTypeService } from './mechanic-type.service';

@Component({
    selector: 'jhi-mechanic-type-delete-dialog',
    templateUrl: './mechanic-type-delete-dialog.component.html'
})
export class MechanicTypeDeleteDialogComponent {

    mechanicType: MechanicType;

    constructor(
        private mechanicTypeService: MechanicTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mechanicTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mechanicTypeListModification',
                content: 'Deleted an mechanicType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mechanic-type-delete-popup',
    template: ''
})
export class MechanicTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mechanicTypePopupService: MechanicTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mechanicTypePopupService
                .open(MechanicTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
