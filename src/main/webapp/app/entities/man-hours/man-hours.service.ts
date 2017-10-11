import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ManHours } from './man-hours.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ManHoursService {

    private resourceUrl = SERVER_API_URL + 'api/man-hours';

    constructor(private http: Http) { }

    create(manHours: ManHours): Observable<ManHours> {
        const copy = this.convert(manHours);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(manHours: ManHours): Observable<ManHours> {
        const copy = this.convert(manHours);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ManHours> {
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
     * Convert a returned JSON object to ManHours.
     */
    private convertItemFromServer(json: any): ManHours {
        const entity: ManHours = Object.assign(new ManHours(), json);
        return entity;
    }

    /**
     * Convert a ManHours to a JSON which can be sent to the server.
     */
    private convert(manHours: ManHours): ManHours {
        const copy: ManHours = Object.assign({}, manHours);
        return copy;
    }
}
