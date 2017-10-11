import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { GroupEngineer } from './group-engineer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GroupEngineerService {

    private resourceUrl = SERVER_API_URL + 'api/group-engineers';

    constructor(private http: Http) { }

    create(groupEngineer: GroupEngineer): Observable<GroupEngineer> {
        const copy = this.convert(groupEngineer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(groupEngineer: GroupEngineer): Observable<GroupEngineer> {
        const copy = this.convert(groupEngineer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<GroupEngineer> {
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
     * Convert a returned JSON object to GroupEngineer.
     */
    private convertItemFromServer(json: any): GroupEngineer {
        const entity: GroupEngineer = Object.assign(new GroupEngineer(), json);
        return entity;
    }

    /**
     * Convert a GroupEngineer to a JSON which can be sent to the server.
     */
    private convert(groupEngineer: GroupEngineer): GroupEngineer {
        const copy: GroupEngineer = Object.assign({}, groupEngineer);
        return copy;
    }
}
