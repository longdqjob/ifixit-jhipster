/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IfixitTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GroupEngineerDetailComponent } from '../../../../../../main/webapp/app/entities/group-engineer/group-engineer-detail.component';
import { GroupEngineerService } from '../../../../../../main/webapp/app/entities/group-engineer/group-engineer.service';
import { GroupEngineer } from '../../../../../../main/webapp/app/entities/group-engineer/group-engineer.model';

describe('Component Tests', () => {

    describe('GroupEngineer Management Detail Component', () => {
        let comp: GroupEngineerDetailComponent;
        let fixture: ComponentFixture<GroupEngineerDetailComponent>;
        let service: GroupEngineerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IfixitTestModule],
                declarations: [GroupEngineerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GroupEngineerService,
                    JhiEventManager
                ]
            }).overrideTemplate(GroupEngineerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GroupEngineerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupEngineerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GroupEngineer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.groupEngineer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
