import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IfixitSharedModule } from '../../shared';
import {
    StockItemService,
    StockItemPopupService,
    StockItemComponent,
    StockItemDetailComponent,
    StockItemDialogComponent,
    StockItemPopupComponent,
    StockItemDeletePopupComponent,
    StockItemDeleteDialogComponent,
    stockItemRoute,
    stockItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...stockItemRoute,
    ...stockItemPopupRoute,
];

@NgModule({
    imports: [
        IfixitSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StockItemComponent,
        StockItemDetailComponent,
        StockItemDialogComponent,
        StockItemDeleteDialogComponent,
        StockItemPopupComponent,
        StockItemDeletePopupComponent,
    ],
    entryComponents: [
        StockItemComponent,
        StockItemDialogComponent,
        StockItemPopupComponent,
        StockItemDeleteDialogComponent,
        StockItemDeletePopupComponent,
    ],
    providers: [
        StockItemService,
        StockItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitStockItemModule {}
