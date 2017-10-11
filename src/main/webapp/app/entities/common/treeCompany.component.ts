import { Component } from '@angular/core';
import { TreeModule } from 'angular-tree-component';

@Component({
    selector: 'tree-company',
    template: `
    <ul>
        <li *ngFor="let item of nodes">{{item.name}}</li>
    </ul>
    <tree-root [nodes]="nodes">
        <ng-template #treeNodeTemplate let-node let-index="index">
          <span>{{ node.data.name }}</span>
          <button (click)="go($event)">Custom Action</button>
        </ng-template>
      </tree-root>
    `, 
})
export class TreeCompanyComponent {
    nodes = [
    {
      id: 1,
      name: 'root1',
      children: [
        { id: 2, name: 'child1' },
        { id: 3, name: 'child2' }
      ]
    },
    {
      id: 4,
      name: 'root2',
      children: [
        { id: 5, name: 'child2.1' },
        {
          id: 6,
          name: 'child2.2',
          children: [
            { id: 7, name: 'subsub' }
          ]
        }
      ]
    }
  ];
}