import { Component } from '@angular/core';

import { TreeModule } from 'ng2-tree';
import { TreeModel, NodeEvent } from 'ng2-tree';

@Component({
  templateUrl: 'tree.component.html',
  selector: 'app-tree-grid',
})
//
export class TreeGridComponent {
    public tree: TreeModel = {
    value: 'Programming languages by programming paradigm',
    children: [{
        id: 1,
        value: 'Object-oriented programming',
        children: [
          {id: 2,value: 'Java'},
          {id: 3,value: 'C++'},
          {id: 4,value: 'C#'}
        ]
      },{
        id: 5,
        value: 'Prototype-based programming',
        settings: {
            'static': true,
            'rightMenu': true,
            'leftMenu': true,
            'templates': {
              'node': '<i class="fa fa-folder-o fa-lg"></i>',
              'leaf': '<i class="fa fa-file-o fa-lg"></i>',
              'leftMenu': '<i class="fa fa-navicon fa-lg"></i>'
            }
        },
        loadChildren: (callback) => {
            setTimeout(() => {
                callback([
                  {id: 6,value: 'JavaScript'},
                    {id: 7,value: 'CoffeeScript'},
                    {id: 8,value: 'Lua'}
                ]);
              }, 5000);
            }
      }
    ]
  };
  
  // 3 - print caught event to the console
  public logEvent(e: NodeEvent): void {
    console.log(e);
  }
  
  handleSelected(event){
      console.log(event);
      console.log(event.node.id + " - " + event.node.value);
  }
}