import { Component, ViewChild, OnInit } from '@angular/core';
import { ChildComponent } from './child.component';
import { IpService } from './ip.service';

@Component({
  selector: 'app-parent',
  template: `
        <button (click)="addToChild()">Add to child</button>
        <h1>{{ip}}</h1>
        <h3>{{value}}</h3>
        <app-child (myClick)="changeValue($event)"></app-child>
  `,
  providers: [IpService]
})
//
export class ParentComponent implements OnInit {
    @ViewChild(ChildComponent)
    myChild: ChildComponent;
    
    value = 0;
    ip: String;
   
    constructor(private ipService: IpService) { }
    
    ngOnInit() {
        this.ipService.getIp()
        .then(ip => this.ip=ip)
        .catch(err => console.log(err));
    }
    
    changeValue(isAdd: boolean){
        if(isAdd){
            this.value++;
        }else{
            this.value--;    
        }
    }
    
    addToChild(){
        this.myChild.valueChild++;
    }
}