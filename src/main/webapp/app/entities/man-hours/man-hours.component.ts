import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ManHours } from './man-hours.model';
import { ManHoursService } from './man-hours.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-man-hours',
    templateUrl: './man-hours.component.html'
})
export class ManHoursComponent implements OnInit, OnDestroy {
manHours: ManHours[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private manHoursService: ManHoursService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.manHoursService.query().subscribe(
            (res: ResponseWrapper) => {
                this.manHours = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInManHours();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ManHours) {
        return item.id;
    }
    registerChangeInManHours() {
        this.eventSubscriber = this.eventManager.subscribe('manHoursListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
