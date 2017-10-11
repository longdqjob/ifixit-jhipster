import { BaseEntity } from './../../shared';

export const enum WOStatus {
    'OPEN',
    'OVER_DUE',
    'IN_PROGRESS',
    'COMPLETE'
}

export class WorkOrder implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public startTime?: any,
        public endTime?: any,
        public iInterval?: number,
        public isRepeat?: number,
        public task?: string,
        public reason?: string,
        public note?: string,
        public mhTotal?: number,
        public mhTotalCost?: number,
        public stockTotalCost?: number,
        public lastUpdate?: any,
        public status?: WOStatus,
        public workTypeId?: number,
        public groupEngineerId?: number,
        public mechanicId?: number,
    ) {
    }
}
