import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { StockItem } from './stock-item.model';
import { StockItemService } from './stock-item.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-stock-item',
    templateUrl: './stock-item.component.html'
})
export class StockItemComponent implements OnInit, OnDestroy {
stockItems: StockItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stockItemService: StockItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.stockItemService.query().subscribe(
            (res: ResponseWrapper) => {
                this.stockItems = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStockItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: StockItem) {
        return item.id;
    }
    registerChangeInStockItems() {
        this.eventSubscriber = this.eventManager.subscribe('stockItemListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
