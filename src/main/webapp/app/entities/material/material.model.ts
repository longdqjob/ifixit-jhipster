import { BaseEntity } from './../../shared';

export class Material implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public completeCode?: string,
        public name?: string,
        public description?: string,
        public cost?: number,
        public unit?: string,
        public quantity?: number,
        public location?: string,
        public imgUrl?: string,
        public imgPath?: string,
        public specification?: string,
        public itemTypeId?: number,
    ) {
    }
}
