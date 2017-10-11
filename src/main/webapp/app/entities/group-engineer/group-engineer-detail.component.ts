import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GroupEngineer } from './group-engineer.model';
import { GroupEngineerService } from './group-engineer.service';

@Component({
    selector: 'jhi-group-engineer-detail',
    templateUrl: './group-engineer-detail.component.html'
})
export class GroupEngineerDetailComponent implements OnInit, OnDestroy {

    groupEngineer: GroupEngineer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private groupEngineerService: GroupEngineerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGroupEngineers();
    }

    load(id) {
        this.groupEngineerService.find(id).subscribe((groupEngineer) => {
            this.groupEngineer = groupEngineer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGroupEngineers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'groupEngineerListModification',
            (response) => this.load(this.groupEngineer.id)
        );
    }
}
