import { BaseEntity } from './../../shared';

export class MechanicType implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public description?: string,
        public note?: string,
        public specification?: string,
    ) {
    }
}
