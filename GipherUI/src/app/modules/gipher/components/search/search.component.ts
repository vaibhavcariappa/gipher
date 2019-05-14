import { Component, OnInit } from '@angular/core';
import { Gipher} from './../../gipher';
import { Image} from './../../image';
import { GipherService } from 'src/app/modules/gipher/gipher.service';
import { ActivatedRoute }  from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { FormControl, Validators, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

 
  giphers: Array<Gipher>;
  gipherObj: Gipher;
  imageObj: Image;
  searchTag: string;
  id: number;
  statusCode: number;
  errorStatus: string;
  gifName: string;
  searchGifs: Array<Gipher>;



  constructor(
     private gipherService: GipherService,
     private routes: ActivatedRoute,
     private matSnackBar: MatSnackBar
     ) {
    this.giphers = [];
    this.searchTag="";
   }

  ngOnInit() {
  }

   


  searchButtonClick() {
    console.log("searchTag",this.searchTag);
    this.gipherService.saveUserSearches(this.searchTag);

    this.gipherService.saveUserSearches(this.searchTag).subscribe(
      data=>{
      console.log("search:",data);
      this.matSnackBar.open("Search successfully updated!", "", {duration: 2000});
      },
      error=>{
        console.log("error during search update in db",error);
      }
    );

    this.gipherService.getSearchDetails(this.searchTag).subscribe(giphers => 
      {
        console.log(giphers);
      this.giphers = [];

      const data =  giphers['data'];
      this.id = 0;

      data.forEach(gipherBlock => {
        // console.log("gipherBlock", gipherBlock);
        this.imageObj = new Image();
        this.gipherObj = new Gipher();
        this.imageObj.text = gipherBlock["images"]["original"]["url"];
        this.imageObj.size = gipherBlock["images"]["original"]["size"];
        this.gipherObj = gipherBlock;
        this.gipherObj.image = this.imageObj;
        // this.gipherObj.gifName = gipherBlock["title"];
        // this.gipherObj.gifId = gipherBlock["id"];
        // console.log("gipherObj", this.gipherObj);  
        this.giphers.push(this.gipherObj);

        this.searchGifs = this.giphers;
      });
    });
 
  }


  addToFavourites(gipher) {
    console.log('Inside the card container',gipher);
    this.gipherService.addGifToFavourites(gipher).subscribe(
      data=>{ 
        console.log(data);
        this.statusCode = data.status;
        if(this.statusCode === 201) {
          console.log("Success", this.statusCode);
          this.matSnackBar.open("Gif successfully added!","",{duration: 2000});
        }


      },
      error=>{
        this.errorStatus = `${error.status}`;
        const errorMsg = `${error.error.message}`;
        console.log("errorStatus:", this.errorStatus);
        console.log("errorMsg:", errorMsg);
        this.statusCode = parseInt(this.errorStatus, 10);
        if(this.statusCode === 409) {
          this.matSnackBar.open(errorMsg,"",{duration: 2000});
        }
        this.statusCode = 0;


      }
    );
  }


}
