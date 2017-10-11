import { BaseEntity } from './../../shared';

export class WorkType implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public iInterval?: number,
        public isRepeat?: number,
        public task?: string,
    ) {
    }
}
