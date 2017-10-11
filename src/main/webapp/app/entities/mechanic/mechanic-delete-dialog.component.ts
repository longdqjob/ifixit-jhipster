import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mechanic } from './mechanic.model';
import { MechanicPopupService } from './mechanic-popup.service';
import { MechanicService } from './mechanic.service';

@Component({
    selector: 'jhi-mechanic-delete-dialog',
    templateUrl: './mechanic-delete-dialog.component.html'
})
export class MechanicDeleteDialogComponent {

    mechanic: Mechanic;

    constructor(
        private mechanicService: MechanicService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mechanicService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mechanicListModification',
                content: 'Deleted an mechanic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mechanic-delete-popup',
    template: ''
})
export class MechanicDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mechanicPopupService: MechanicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mechanicPopupService
                .open(MechanicDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
