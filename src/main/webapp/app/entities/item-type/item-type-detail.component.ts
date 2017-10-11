import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItemType } from './item-type.model';
import { ItemTypeService } from './item-type.service';

@Component({
    selector: 'jhi-item-type-detail',
    templateUrl: './item-type-detail.component.html'
})
export class ItemTypeDetailComponent implements OnInit, OnDestroy {

    itemType: ItemType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private itemTypeService: ItemTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInItemTypes();
    }

    load(id) {
        this.itemTypeService.find(id).subscribe((itemType) => {
            this.itemType = itemType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInItemTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'itemTypeListModification',
            (response) => this.load(this.itemType.id)
        );
    }
}
