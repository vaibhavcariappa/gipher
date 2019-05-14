import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Gipher } from 'src/app/modules/gipher/gipher';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DialogComponent } from 'src/app/modules/gipher/components/dialog/dialog.component';


@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {


  @Input()
  gipher: Gipher; 
  
  @Input()
  favData: boolean;

  @Input()
  recommend: boolean;
  
  @Output()
  addToFavourites = new EventEmitter();
  
  @Output()
  deleteFromFavourites = new EventEmitter();
  
  @Output()
  updateComments = new EventEmitter();
    
    constructor(private dialog: MatDialog) {
  
    }
  
    addButtonClick(gipher) {
      console.log('add Gif Details:', gipher);
      this.addToFavourites.emit(gipher);
    }
  
    deleteButtonClick(gipher) {
      console.log('delete Gif Details:', gipher);
      this.deleteFromFavourites.emit(gipher);
    }
  
    addComments(){
      const dialogRef = this.dialog.open(DialogComponent, {
        width: "250px",
        data: {comments: this.gipher.comments}
      });
  
      dialogRef.afterClosed().subscribe(result => {
        console.log('Dialog close result:', result);
        if(typeof result!='undefined' && result) {
          this.gipher.comments = result;
          this.updateComments.emit(this.gipher);
        }
      });
    }
  
  
  
    ngOnInit() {
      // console.log('favData', this.favData);
      // console.log('recommend', this.recommend);
    }
  

}
