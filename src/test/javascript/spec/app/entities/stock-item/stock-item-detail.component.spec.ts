/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IfixitTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { StockItemDetailComponent } from '../../../../../../main/webapp/app/entities/stock-item/stock-item-detail.component';
import { StockItemService } from '../../../../../../main/webapp/app/entities/stock-item/stock-item.service';
import { StockItem } from '../../../../../../main/webapp/app/entities/stock-item/stock-item.model';

describe('Component Tests', () => {

    describe('StockItem Management Detail Component', () => {
        let comp: StockItemDetailComponent;
        let fixture: ComponentFixture<StockItemDetailComponent>;
        let service: StockItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IfixitTestModule],
                declarations: [StockItemDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    StockItemService,
                    JhiEventManager
                ]
            }).overrideTemplate(StockItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StockItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new StockItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.stockItem).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
