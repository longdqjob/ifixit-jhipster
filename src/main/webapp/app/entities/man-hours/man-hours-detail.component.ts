import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ManHours } from './man-hours.model';
import { ManHoursService } from './man-hours.service';

@Component({
    selector: 'jhi-man-hours-detail',
    templateUrl: './man-hours-detail.component.html'
})
export class ManHoursDetailComponent implements OnInit, OnDestroy {

    manHours: ManHours;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private manHoursService: ManHoursService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInManHours();
    }

    load(id) {
        this.manHoursService.find(id).subscribe((manHours) => {
            this.manHours = manHours;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInManHours() {
        this.eventSubscriber = this.eventManager.subscribe(
            'manHoursListModification',
            (response) => this.load(this.manHours.id)
        );
    }
}
