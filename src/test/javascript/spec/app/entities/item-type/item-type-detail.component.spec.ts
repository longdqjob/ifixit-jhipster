/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IfixitTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ItemTypeDetailComponent } from '../../../../../../main/webapp/app/entities/item-type/item-type-detail.component';
import { ItemTypeService } from '../../../../../../main/webapp/app/entities/item-type/item-type.service';
import { ItemType } from '../../../../../../main/webapp/app/entities/item-type/item-type.model';

describe('Component Tests', () => {

    describe('ItemType Management Detail Component', () => {
        let comp: ItemTypeDetailComponent;
        let fixture: ComponentFixture<ItemTypeDetailComponent>;
        let service: ItemTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IfixitTestModule],
                declarations: [ItemTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ItemTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(ItemTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItemTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ItemType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.itemType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
