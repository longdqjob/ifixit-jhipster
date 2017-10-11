import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WorkOrder } from './work-order.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WorkOrderService {

    private resourceUrl = SERVER_API_URL + 'api/work-orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(workOrder: WorkOrder): Observable<WorkOrder> {
        const copy = this.convert(workOrder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(workOrder: WorkOrder): Observable<WorkOrder> {
        const copy = this.convert(workOrder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WorkOrder> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to WorkOrder.
     */
    private convertItemFromServer(json: any): WorkOrder {
        const entity: WorkOrder = Object.assign(new WorkOrder(), json);
        entity.startTime = this.dateUtils
            .convertLocalDateFromServer(json.startTime);
        entity.endTime = this.dateUtils
            .convertLocalDateFromServer(json.endTime);
        entity.lastUpdate = this.dateUtils
            .convertLocalDateFromServer(json.lastUpdate);
        return entity;
    }

    /**
     * Convert a WorkOrder to a JSON which can be sent to the server.
     */
    private convert(workOrder: WorkOrder): WorkOrder {
        const copy: WorkOrder = Object.assign({}, workOrder);
        copy.startTime = this.dateUtils
            .convertLocalDateToServer(workOrder.startTime);
        copy.endTime = this.dateUtils
            .convertLocalDateToServer(workOrder.endTime);
        copy.lastUpdate = this.dateUtils
            .convertLocalDateToServer(workOrder.lastUpdate);
        return copy;
    }
}
