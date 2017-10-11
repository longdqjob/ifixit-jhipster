import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IfixitCompanyModule } from './company/company.module';
import { IfixitGroupEngineerModule } from './group-engineer/group-engineer.module';
import { IfixitItemTypeModule } from './item-type/item-type.module';
import { IfixitMechanicTypeModule } from './mechanic-type/mechanic-type.module';
import { IfixitMechanicModule } from './mechanic/mechanic.module';
import { IfixitMaterialModule } from './material/material.module';
import { IfixitWorkTypeModule } from './work-type/work-type.module';
import { IfixitWorkOrderModule } from './work-order/work-order.module';
import { IfixitStockItemModule } from './stock-item/stock-item.module';
import { IfixitManHoursModule } from './man-hours/man-hours.module';
import { IfixitUserExtraModule } from './user-extra/user-extra.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        IfixitCompanyModule,
        IfixitGroupEngineerModule,
        IfixitItemTypeModule,
        IfixitMechanicTypeModule,
        IfixitMechanicModule,
        IfixitMaterialModule,
        IfixitWorkTypeModule,
        IfixitWorkOrderModule,
        IfixitStockItemModule,
        IfixitManHoursModule,
        IfixitUserExtraModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IfixitEntityModule {}
