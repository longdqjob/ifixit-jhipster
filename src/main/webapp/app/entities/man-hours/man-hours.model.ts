import { BaseEntity } from './../../shared';

export class ManHours implements BaseEntity {
    constructor(
        public id?: number,
        public mh?: number,
        public workOrderId?: number,
        public groupEngineerId?: number,
    ) {
    }
}
