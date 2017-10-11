/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IfixitTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WorkOrderDetailComponent } from '../../../../../../main/webapp/app/entities/work-order/work-order-detail.component';
import { WorkOrderService } from '../../../../../../main/webapp/app/entities/work-order/work-order.service';
import { WorkOrder } from '../../../../../../main/webapp/app/entities/work-order/work-order.model';

describe('Component Tests', () => {

    describe('WorkOrder Management Detail Component', () => {
        let comp: WorkOrderDetailComponent;
        let fixture: ComponentFixture<WorkOrderDetailComponent>;
        let service: WorkOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IfixitTestModule],
                declarations: [WorkOrderDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WorkOrderService,
                    JhiEventManager
                ]
            }).overrideTemplate(WorkOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WorkOrder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.workOrder).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
