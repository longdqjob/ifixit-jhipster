import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { WorkType } from './work-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WorkTypeService {

    private resourceUrl = SERVER_API_URL + 'api/work-types';

    constructor(private http: Http) { }

    create(workType: WorkType): Observable<WorkType> {
        const copy = this.convert(workType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(workType: WorkType): Observable<WorkType> {
        const copy = this.convert(workType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WorkType> {
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
     * Convert a returned JSON object to WorkType.
     */
    private convertItemFromServer(json: any): WorkType {
        const entity: WorkType = Object.assign(new WorkType(), json);
        return entity;
    }

    /**
     * Convert a WorkType to a JSON which can be sent to the server.
     */
    private convert(workType: WorkType): WorkType {
        const copy: WorkType = Object.assign({}, workType);
        return copy;
    }
}
