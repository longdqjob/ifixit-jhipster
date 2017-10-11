/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IfixitTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MechanicTypeDetailComponent } from '../../../../../../main/webapp/app/entities/mechanic-type/mechanic-type-detail.component';
import { MechanicTypeService } from '../../../../../../main/webapp/app/entities/mechanic-type/mechanic-type.service';
import { MechanicType } from '../../../../../../main/webapp/app/entities/mechanic-type/mechanic-type.model';

describe('Component Tests', () => {

    describe('MechanicType Management Detail Component', () => {
        let comp: MechanicTypeDetailComponent;
        let fixture: ComponentFixture<MechanicTypeDetailComponent>;
        let service: MechanicTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IfixitTestModule],
                declarations: [MechanicTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MechanicTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(MechanicTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MechanicTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MechanicTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MechanicType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mechanicType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
