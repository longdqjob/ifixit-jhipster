import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MechanicType } from './mechanic-type.model';
import { MechanicTypeService } from './mechanic-type.service';

@Component({
    selector: 'jhi-mechanic-type-detail',
    templateUrl: './mechanic-type-detail.component.html'
})
export class MechanicTypeDetailComponent implements OnInit, OnDestroy {

    mechanicType: MechanicType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mechanicTypeService: MechanicTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMechanicTypes();
    }

    load(id) {
        this.mechanicTypeService.find(id).subscribe((mechanicType) => {
            this.mechanicType = mechanicType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMechanicTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mechanicTypeListModification',
            (response) => this.load(this.mechanicType.id)
        );
    }
}
