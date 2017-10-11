import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ManHours } from './man-hours.model';
import { ManHoursPopupService } from './man-hours-popup.service';
import { ManHoursService } from './man-hours.service';

@Component({
    selector: 'jhi-man-hours-delete-dialog',
    templateUrl: './man-hours-delete-dialog.component.html'
})
export class ManHoursDeleteDialogComponent {

    manHours: ManHours;

    constructor(
        private manHoursService: ManHoursService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.manHoursService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'manHoursListModification',
                content: 'Deleted an manHours'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-man-hours-delete-popup',
    template: ''
})
export class ManHoursDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private manHoursPopupService: ManHoursPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.manHoursPopupService
                .open(ManHoursDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
