import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Gipher } from 'src/app/modules/gipher/gipher';
import { USER_NAME } from 'src/app/modules/authentication/authentication.service';
import { HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class GipherService {

  thirdPartyApi: string;
  apiType: string;
  apiKey: string;
  limit: string;
  rating: string;
  springEndPoint: string;
  recommendEndPoint: string;
  username: string;
  byPassCorsUrl: string;

  constructor(private httpClient: HttpClient) { 

    this.thirdPartyApi = "http://api.giphy.com/v1/gifs/";
    this.apiType = "";
    this.apiKey = "api_key=EFKWFoKbodZact28qxZUENiLLfzrg7iL&";
    this.limit = "limit=50&";
    this.rating = "rating=G";
    this.byPassCorsUrl = "https://cors-anywhere.herokuapp.com/";
 
    
    //this.springEndPoint = "http://localhost:8085/api/v1/usertrackservice/";
    this.springEndPoint = "http://localhost:9087/giphermanager/api/v1/giphermanager/";
    this.recommendEndPoint = "http://localhost:9087/gipherrecommendersystem/api/v1/gipherrecommendersystem";

  }

  getGifDetails(): Observable<any> {
    this.apiType = "trending?";
    const url = this.byPassCorsUrl + this.thirdPartyApi + this.apiType + this.apiKey + this.limit + this.rating;
    return this.httpClient.get(url);
  }  

  getSearchDetails(searchTag: string): Observable<any> {
    this.username = sessionStorage.getItem(USER_NAME);
    console.log("searchTag", searchTag);
    this.apiType = "search?";
    searchTag = "q=" + searchTag + "?";
    const url = this.byPassCorsUrl+ this.thirdPartyApi + this.apiType + this.apiKey + searchTag + this.limit + this.rating;
    return this.httpClient.get(url);
  }  

  addGifToFavourites(gipher: Gipher){
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.springEndPoint + "user/" + this.username + "/gipher";
    console.log("New url: " + url);
    return this.httpClient.post(url, gipher, {observe: "response"});
  }

  getAllGifsFromFavourites(){
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.springEndPoint + "user/" + this.username + "/giphers";
    return this.httpClient.get<Gipher[]>(url);
  }

  deleteGifFromFavourites(gipher: Gipher) {
    this.username = sessionStorage.getItem(USER_NAME);
    console.log("deleteGifFromFavourites gipher", gipher);
    console.log("gipher.id", gipher.id);
    const url = this.springEndPoint + "user/" + this.username + "/" + gipher.id;
    return this.httpClient.delete(url);
  }  

  updateComments(gipher) {
    this.username = sessionStorage.getItem(USER_NAME);
    console.log("gipher.comments", gipher.comments);
    const url = this.springEndPoint + "user/" + this.username + "/gipher";
    return this.httpClient.patch(url, gipher, {observe: "response"});
  }  

  getAllGifsFromRecommended(){
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.recommendEndPoint + "/giphers";
    return this.httpClient.get<Gipher[]>(url);
  }

  saveUserSearches(searchTag: string) {
    this.username = sessionStorage.getItem(USER_NAME);
    console.log("searchTag before saving in db", searchTag);
    const url = this.springEndPoint + "user/" + this.username + "/search/" + searchTag;
    console.log("endPoint url ", url);
    return this.httpClient.post(url,searchTag, {observe: "response"});    
  }

  filterArtists(giphers: Array<Gipher>, gifName: string) {
    const results = giphers.filter(gipher =>{
      return gipher.title.match(gifName);
    });
    console.log("Filtered Artists" , results);
    return results;
  }


}
