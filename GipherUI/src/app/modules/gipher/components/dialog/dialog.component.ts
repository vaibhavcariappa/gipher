import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { Gipher } from '../../gipher';
import { MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  comments: string;

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Gipher
    ) { 
      this.comments = data.comments;
    }

  ngOnInit() {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  updateComments() { 
    this.dialogRef.close(this.comments); 

  }
}
