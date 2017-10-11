import { Component, Injectable, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import {
    TreeviewI18n, TreeviewItem, TreeviewConfig, TreeviewHelper, TreeviewComponent,
    TreeviewEventParser, OrderDownlineTreeviewEventParser, DownlineTreeviewItem
} from 'ng2-dropdown-treeview';
import { TreeItemService } from './treeItem.service';
import { ContextMenuModule  } from 'angular2-contextmenu';
import { ContextMenuComponent } from 'angular2-contextmenu/src/contextMenu.component';
import { ContextMenuService } from 'angular2-contextmenu/src/contextMenu.service';

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
    let-onCheckedChange="onCheckedChange" >
    <div class="row" (dblclick)="onDblclick(item)">
        <div class="col-xs-6 col-sm-6" [style.padding-left]="getStyleLevel(item)">
            <div class="form-check" >
                <i *ngIf="item.children" (click)="toggleCollapseExpand()" aria-hidden="true"
                    class="fa" [class.fa-caret-right]="item.collapsed" [class.fa-caret-down]="!item.collapsed"></i>


                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input"
                           [(ngModel)]="item.checked" (ngModelChange)="onCheckedChange()" [disabled]="item.disabled" />
                    {{item.text}}
                </label>


            </div>
        </div>
        <div class="col-xs-4 col-sm-4">value: {{item.value.cost }}</div>
        <div class="col-xs-2 col-sm-2" style="padding-right: 5px;">
            <label class="form-check-label">
                <i class="fa fa-trash" aria-hidden="true" title="Remove" (click)="removeItem(item)"></i>
            </label>
        </div>
    </div>
</template>
<div class="row">
    <div class="col-8">
        <div class="form-group">
            <!--<div class="d-inline-block">-->
            <div>
                <leo-treeview [items]="items" [template]="tpl" (selectedChange)="onSelectedChange($event)">
                </leo-treeview>
                <context-menu></context-menu>
            </div>
        </div>
    </div>
    <div class="col-4">
    <ul>
    <li *ngFor="let item of tesst" (contextmenu)="onContextMenu($event, item)">Right Click df: {{item.name}}</li>
</ul>
<context-menu></context-menu>

        <div class="alert alert-success" role="alert">
            Selected products:
            <div *ngFor="let row of rows">{{row}}</div>
        </div>
    </div>
</div>
`, providers: [
        TreeItemService, ContextMenuService, 
        { provide: TreeviewEventParser, useClass: OrderDownlineTreeviewEventParser },
        { provide: TreeviewConfig, useClass: ItemTreeviewConfig }
    ]
})
export class TreeItemComponent implements OnInit {
    @ViewChild(TreeviewComponent) treeviewComponent: TreeviewComponent;
    items: TreeviewItem[];
    rows: string[];

    constructor(
        private contextMenuService: ContextMenuService,
        private service: TreeItemService
    ) { }

    ngOnInit() {
        this.items = this.service.getItems();
    }

    onItemCheckedChange(item: TreeviewItem) {
        console.log(item);
    }
    onDblclick(item: TreeviewItem){
        console.log("------onDblclick----------" + item.value.id);
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
            const row = `${reverseTexts.join(' -> ')} : ${value.id}`;
            this.rows.push(row);
        });
    }

    removeItem(item: TreeviewItem) {
        TreeviewHelper.removeItem(item, this.items);
        this.treeviewComponent.raiseSelectedChange();
    }
    
    getStyleLevel(item: TreeviewItem){
        if(item){
            return item.value.level * 2 + "rem";
        }
    }
    
    //Context
    public menuOptions = [
        {
          html: () => 'Buy',
          click: (item, $event) => {
            console.log("-----------Buy: " + item.value.id);
          },
        },
        {
          html: (): string => 'Sell',
          click: (item, $event): void => {
            console.log("-----------Sell: " + item.value.id);
          },
          enabled: (item): boolean => {
            return item.value.level > 0;
          }
        },
      ];
      
      public tesst = [
      { name: 'John', otherProperty: 'Foo' },
      { name: 'Joe', otherProperty: 'Bar' }
  ];
  
      public onContextMenu($event: MouseEvent, item: any): void {
          console.log("----------onContextMenu---------");
          this.contextMenuService.show.next({
      actions: [
        {
          html: (item) => `Say hi!`,
          click: (item) => alert('Hi, ' + item.name)
        },
        {
          html: (item) => `Something else`,
          click: (item) => alert('Or not...')
        },
      ],
      event: $event,
      item: item,
    });
    console.log("----------onContextMenu--11-------");
    $event.preventDefault();
    console.log("----------onContextMenu--222-------");
//    $event.stopPropagation();
//    console.log("----------onContextMenu--333-------");
//        this.contextMenuService.show.next({
//          actions: this.menuOptions,
//          event: $event,
//          item: item,
//        });
//        $event.preventDefault();
      }
}