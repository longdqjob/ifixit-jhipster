import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Mechanic } from './mechanic.model';
import { MechanicService } from './mechanic.service';

@Injectable()
export class MechanicPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mechanicService: MechanicService

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
                this.mechanicService.find(id).subscribe((mechanic) => {
                    if (mechanic.since) {
                        mechanic.since = {
                            year: mechanic.since.getFullYear(),
                            month: mechanic.since.getMonth() + 1,
                            day: mechanic.since.getDate()
                        };
                    }
                    this.ngbModalRef = this.mechanicModalRef(component, mechanic);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mechanicModalRef(component, new Mechanic());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mechanicModalRef(component: Component, mechanic: Mechanic): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mechanic = mechanic;
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
