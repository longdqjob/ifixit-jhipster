import { BaseEntity } from './../../shared';

export class ItemType implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public completeCode?: string,
        public name?: string,
        public specification?: string,
        public itemTypes?: BaseEntity[],
        public parentId?: number,
    ) {
    }
}
