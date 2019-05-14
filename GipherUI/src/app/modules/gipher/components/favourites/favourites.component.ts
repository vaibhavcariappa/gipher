import { Component, OnInit } from '@angular/core';
import { GipherService } from 'src/app/modules/gipher/gipher.service';
import { Gipher } from './../../gipher';
import { MatSnackBar } from '@angular/material';


@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.css']
})
export class FavouritesComponent implements OnInit {

  giphers: Array<Gipher>;
  favData = true;

  constructor(
    private gipherService: GipherService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    const message = "Favourites is empty!";
    this.gipherService.getAllGifsFromFavourites().subscribe(data=>{
      this.giphers = data;
      if(!this.giphers || data.length === 0) {
        this.snackBar.open(message,"",{duration: 2000});
      }
    });
  }

  deleteFromFavourites(gipher) {
    this.gipherService.deleteGifFromFavourites(gipher).subscribe(data=>{
      console.log("Deleted:",data);
      const index = this.giphers.indexOf(gipher);
      this.giphers.splice(index, 1);
      this.snackBar.open("Successfully Deleted!", "", {duration: 2000});

    });
    return this.giphers;
  }

  updateComments(gipher) {
    this.gipherService.updateComments(gipher).subscribe(
      data=>{
      console.log("comments:",data);
      this.snackBar.open("Comments successfully updated!", "", {duration: 2000});
      },
      error=>{
        console.log("error during update in wish-listcomponent",error);
      }
    );
  }


}
