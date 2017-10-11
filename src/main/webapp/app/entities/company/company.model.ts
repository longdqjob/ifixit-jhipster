import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public completeCode?: string,
        public name?: string,
        public description?: string,
        public state?: number,
        public parentId?: number,
    ) {
    }
}
