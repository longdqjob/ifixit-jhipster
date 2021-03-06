import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { StockItem } from './stock-item.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StockItemService {

    private resourceUrl = SERVER_API_URL + 'api/stock-items';

    constructor(private http: Http) { }

    create(stockItem: StockItem): Observable<StockItem> {
        const copy = this.convert(stockItem);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(stockItem: StockItem): Observable<StockItem> {
        const copy = this.convert(stockItem);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<StockItem> {
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
     * Convert a returned JSON object to StockItem.
     */
    private convertItemFromServer(json: any): StockItem {
        const entity: StockItem = Object.assign(new StockItem(), json);
        return entity;
    }

    /**
     * Convert a StockItem to a JSON which can be sent to the server.
     */
    private convert(stockItem: StockItem): StockItem {
        const copy: StockItem = Object.assign({}, stockItem);
        return copy;
    }
}
