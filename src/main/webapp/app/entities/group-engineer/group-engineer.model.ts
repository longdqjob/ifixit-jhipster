import { BaseEntity } from './../../shared';

export class GroupEngineer implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public completeCode?: string,
        public name?: string,
        public description?: string,
        public cost?: number,
        public parentId?: number,
    ) {
    }
}
