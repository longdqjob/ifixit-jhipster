import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Material } from './material.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MaterialService {

    private resourceUrl = SERVER_API_URL + 'api/materials';

    constructor(private http: Http) { }

    create(material: Material): Observable<Material> {
        const copy = this.convert(material);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(material: Material): Observable<Material> {
        const copy = this.convert(material);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Material> {
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
     * Convert a returned JSON object to Material.
     */
    private convertItemFromServer(json: any): Material {
        const entity: Material = Object.assign(new Material(), json);
        return entity;
    }

    /**
     * Convert a Material to a JSON which can be sent to the server.
     */
    private convert(material: Material): Material {
        const copy: Material = Object.assign({}, material);
        return copy;
    }
}
