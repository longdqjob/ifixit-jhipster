import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ItemType } from './item-type.model';
import { ItemTypePopupService } from './item-type-popup.service';
import { ItemTypeService } from './item-type.service';

@Component({
    selector: 'jhi-item-type-delete-dialog',
    templateUrl: './item-type-delete-dialog.component.html'
})
export class ItemTypeDeleteDialogComponent {

    itemType: ItemType;

    constructor(
        private itemTypeService: ItemTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'itemTypeListModification',
                content: 'Deleted an itemType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-type-delete-popup',
    template: ''
})
export class ItemTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itemTypePopupService: ItemTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.itemTypePopupService
                .open(ItemTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
