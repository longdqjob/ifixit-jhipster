/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IfixitTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ManHoursDetailComponent } from '../../../../../../main/webapp/app/entities/man-hours/man-hours-detail.component';
import { ManHoursService } from '../../../../../../main/webapp/app/entities/man-hours/man-hours.service';
import { ManHours } from '../../../../../../main/webapp/app/entities/man-hours/man-hours.model';

describe('Component Tests', () => {

    describe('ManHours Management Detail Component', () => {
        let comp: ManHoursDetailComponent;
        let fixture: ComponentFixture<ManHoursDetailComponent>;
        let service: ManHoursService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IfixitTestModule],
                declarations: [ManHoursDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ManHoursService,
                    JhiEventManager
                ]
            }).overrideTemplate(ManHoursDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ManHoursDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManHoursService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ManHours(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.manHours).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
