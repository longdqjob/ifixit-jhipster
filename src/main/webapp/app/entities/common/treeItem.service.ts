import { Injectable } from '@angular/core';
import { TreeviewItem } from 'ng2-dropdown-treeview';

export class TreeItemService {
    getItems(): TreeviewItem[] {
        const fruitCategory = new TreeviewItem({
            text: 'Fruit', value: {id: 1, cost: 30, level: 0}, children: [
                { text: 'Apple',  value: {id: 11, cost: 30, level: 1} ,
                    children: [
                        { text: 'Apple1',  value: {id: 31, cost: 30, level: 2} },
                        { text: 'Apple2',  value: {id: 32, cost: 32, level: 2} }
                    ]},
                { text: 'Mango',  value: {id: 12, cost: 30, level: 1} }
            ]
        });
        const vegetableCategory = new TreeviewItem({
            text: 'Vegetable', value: {id: 2, cost: 30, level: 0}, children: [
                { text: 'Salad',  value: {id: 21, cost: 30, level: 1} },
                { text: 'Potato',  value: {id: 22, cost: 30, level: 1} }
            ]
        });
        vegetableCategory.children.push(new TreeviewItem({ text: 'Mushroom', value: {id: 23, cost: 23, level: 1},  checked: false }));
        vegetableCategory.correctChecked(); // need this to make 'Vegetable' node to change checked value from true to false
        return [fruitCategory, vegetableCategory];
    }
}