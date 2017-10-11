import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { WorkOrder } from './work-order.model';
import { WorkOrderService } from './work-order.service';

@Injectable()
export class WorkOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private workOrderService: WorkOrderService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.workOrderService.find(id).subscribe((workOrder) => {
                    if (workOrder.startTime) {
                        workOrder.startTime = {
                            year: workOrder.startTime.getFullYear(),
                            month: workOrder.startTime.getMonth() + 1,
                            day: workOrder.startTime.getDate()
                        };
                    }
                    if (workOrder.endTime) {
                        workOrder.endTime = {
                            year: workOrder.endTime.getFullYear(),
                            month: workOrder.endTime.getMonth() + 1,
                            day: workOrder.endTime.getDate()
                        };
                    }
                    if (workOrder.lastUpdate) {
                        workOrder.lastUpdate = {
                            year: workOrder.lastUpdate.getFullYear(),
                            month: workOrder.lastUpdate.getMonth() + 1,
                            day: workOrder.lastUpdate.getDate()
                        };
                    }
                    this.ngbModalRef = this.workOrderModalRef(component, workOrder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.workOrderModalRef(component, new WorkOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    workOrderModalRef(component: Component, workOrder: WorkOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.workOrder = workOrder;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
