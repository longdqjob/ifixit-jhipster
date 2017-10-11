import { Component, Injectable, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import {
    TreeviewI18n, TreeviewItem, TreeviewConfig, TreeviewHelper, TreeviewComponent,
    TreeviewEventParser, OrderDownlineTreeviewEventParser, DownlineTreeviewItem
} from 'ng2-dropdown-treeview';
import { TreeItemService } from './treeItem.service';

@Injectable()
export class ItemTreeviewConfig extends TreeviewConfig {
    isShowAllCheckBox = true;
    isShowFilter = true;
    isShowCollapseExpand = false;
    maxHeight = 500;
}

@Component({
    selector: 'leo-item',
    template: `
<template #tpl let-item="item"
    let-toggleCollapseExpand="toggleCollapseExpand"
    let-onCheckedChange="onCheckedChange">
    <div class="form-check">
        <i *ngIf="item.children" (click)="toggleCollapseExpand()" aria-hidden="true"
            class="fa" [class.fa-caret-right]="item.collapsed" [class.fa-caret-down]="!item.collapsed"></i>
        <label class="form-check-label">
            <input type="checkbox" class="form-check-input"
                [(ngModel)]="item.checked" (ngModelChange)="onCheckedChange()" [disabled]="item.disabled" />
            {{item.text}}
        </label>
        <label class="form-check-label">
            <i class="fa fa-trash" aria-hidden="true" title="Remove" (click)="removeItem(item)"></i>
        </label>
    </div>
</template>
<div class="row">
    <div class="col-6">
        <div class="form-group">
            <div class="d-inline-block">
                <leo-treeview [items]="items" [template]="tpl" (selectedChange)="onSelectedChange($event)">
                </leo-treeview>
            </div>
        </div>
    </div>
    <div class="col-6">
        <div class="alert alert-success" role="alert">
            Selected products:
            <div *ngFor="let row of rows">{{row}}</div>
        </div>
    </div>
</div>
`, providers: [
        TreeItemService,
        { provide: TreeviewEventParser, useClass: OrderDownlineTreeviewEventParser },
        { provide: TreeviewConfig, useClass: ItemTreeviewConfig }
    ]
})
export class TreeItemComponent implements OnInit {
    @ViewChild(TreeviewComponent) treeviewComponent: TreeviewComponent;
    items: TreeviewItem[];
    rows: string[];

    constructor(
        private service: TreeItemService
    ) { }

    ngOnInit() {
        this.items = this.service.getItems();
    }

    onItemCheckedChange(item: TreeviewItem) {
        console.log(item);
    }

    onSelectedChange(downlineItems: DownlineTreeviewItem[]) {
        this.rows = [];
        downlineItems.forEach(downlineItem => {
            const item = downlineItem.item;
            const value = item.value;
            const texts = [item.text];
            let parent = downlineItem.parent;
            while (!_.isNil(parent)) {
                texts.push(parent.item.text);
                parent = parent.parent;
            }
            const reverseTexts = _.reverse(texts);
            const row = `${reverseTexts.join(' -> ')} : ${value}`;
            this.rows.push(row);
        });
    }

    removeItem(item: TreeviewItem) {
        TreeviewHelper.removeItem(item, this.items);
        this.treeviewComponent.raiseSelectedChange();
    }
}