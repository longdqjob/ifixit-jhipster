import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GroupEngineer } from './group-engineer.model';
import { GroupEngineerPopupService } from './group-engineer-popup.service';
import { GroupEngineerService } from './group-engineer.service';

@Component({
    selector: 'jhi-group-engineer-delete-dialog',
    templateUrl: './group-engineer-delete-dialog.component.html'
})
export class GroupEngineerDeleteDialogComponent {

    groupEngineer: GroupEngineer;

    constructor(
        private groupEngineerService: GroupEngineerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groupEngineerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'groupEngineerListModification',
                content: 'Deleted an groupEngineer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-group-engineer-delete-popup',
    template: ''
})
export class GroupEngineerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groupEngineerPopupService: GroupEngineerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.groupEngineerPopupService
                .open(GroupEngineerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
