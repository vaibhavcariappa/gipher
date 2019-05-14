import { Component, OnInit } from '@angular/core';
import { Gipher} from './../../gipher';
import { Image} from './../../image';
import { GipherService } from 'src/app/modules/gipher/gipher.service';
import { ActivatedRoute }  from '@angular/router';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-card-container',
  templateUrl: './card-container.component.html',
  styleUrls: ['./card-container.component.css']
})
export class CardContainerComponent implements OnInit {


  giphers: Array<Gipher>;
  gipherObj: Gipher;
  imageObj: Image;
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
   }

  ngOnInit() {


    this.gipherService.getGifDetails().subscribe(giphers => 
      {
        console.log(giphers);
      this.giphers = [];

      const data =  giphers['data'];
      this.id = 0;

      data.forEach(gipherBlock => {
        //console.log("gipherBlock", gipherBlock);
        this.imageObj = new Image();
        this.gipherObj = new Gipher();
        this.imageObj.text = gipherBlock["images"]["original"]["url"];
        this.imageObj.size = gipherBlock["images"]["original"]["size"];
        this.gipherObj = gipherBlock;
        this.gipherObj.image = this.imageObj;
        // this.gipherObj.gifName = gipherBlock["title"];
        // this.gipherObj.gifId = gipherBlock["id"];
        //console.log("gipherObj", this.gipherObj);  
        this.giphers.push(this.gipherObj);

        this.searchGifs = this.giphers;
      });
    });
    
  }


  onKey(event: any) {
    this.gifName = event.target.value;
    console.log("gifName", this.gifName);

    const result = this.searchGifs.filter(gipher => {
      return gipher.title.match(this.gifName);
    });
    console.log(result, "Filtered Data");
    this.giphers = result;
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
