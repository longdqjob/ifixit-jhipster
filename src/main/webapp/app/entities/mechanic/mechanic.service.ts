import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Mechanic } from './mechanic.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MechanicService {

    private resourceUrl = SERVER_API_URL + 'api/mechanics';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(mechanic: Mechanic): Observable<Mechanic> {
        const copy = this.convert(mechanic);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mechanic: Mechanic): Observable<Mechanic> {
        const copy = this.convert(mechanic);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Mechanic> {
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
     * Convert a returned JSON object to Mechanic.
     */
    private convertItemFromServer(json: any): Mechanic {
        const entity: Mechanic = Object.assign(new Mechanic(), json);
        entity.since = this.dateUtils
            .convertLocalDateFromServer(json.since);
        return entity;
    }

    /**
     * Convert a Mechanic to a JSON which can be sent to the server.
     */
    private convert(mechanic: Mechanic): Mechanic {
        const copy: Mechanic = Object.assign({}, mechanic);
        copy.since = this.dateUtils
            .convertLocalDateToServer(mechanic.since);
        return copy;
    }
    
    //ThuyetLV Add
    getMechanics(id: number,req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/all/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }
}
