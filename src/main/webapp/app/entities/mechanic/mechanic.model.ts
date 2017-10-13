import { BaseEntity } from './../../shared';

export class Mechanic implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public completeCode?: string,
        public name?: string,
        public description?: string,
        public note?: string,
        public since?: any,
        public specification?: string,
        public location?: string,
        public imgUrl?: string,
        public imgPath?: string,
        public itemTypeId?: number,
        public mechanicTypeId?: number,
        public companyId?: number,
        //Add
        public itemTypeCode?: number,
        public itemTypeName?: number,
        public itemTypeSpe?: number,
        public mechanicTypeCode?: number,
        public mechanicTypeName?: number,
        public companyCode?: number,
        public companyName?: number,
        
    ) {
    }
}
