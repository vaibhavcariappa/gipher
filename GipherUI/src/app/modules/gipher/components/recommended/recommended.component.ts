import { Component, OnInit } from '@angular/core';
import { GipherService } from 'src/app/modules/gipher/gipher.service';
import { Gipher } from './../../gipher';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-recommended',
  templateUrl: './recommended.component.html',
  styleUrls: ['./recommended.component.css']
})
export class RecommendedComponent implements OnInit {

  giphers: Array<Gipher>;
  favData = false;
  recommend = true;

  constructor(
    private gipherService: GipherService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    const message = "Recommended List is empty!";
    this.gipherService.getAllGifsFromRecommended().subscribe(data=>{
      this.giphers = data;
      if(!this.giphers || data.length === 0) {
        this.snackBar.open(message,"",{duration: 2000});
      }
    });
  }

}
