import { TestBed } from '@angular/core/testing';

import { GipherService } from './gipher.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Gipher } from './gipher';

describe('GipherService', () => {


  let gipher = new Gipher();
  gipher = {
    id: "gif001",
    title: "gifTitle",
    url: "url.com",
    source: "giphy",
    rating: "g",
    comments: "nice gif",
    counter: 1,
    image: {
      imageId: 1,
      text: "imagetext",
      size: "large"
    }
  };

  const searchText = "cheeseburger";

  const springEndPoint = "http://localhost:9087/giphermanager/api/v1/giphermanager/";
  const recommendEndPoint = "http://localhost:9087/gipherrecommendersystem/api/v1/gipherrecommendersystem/";

  let gipherService: GipherService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => { 
    
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [GipherService]
    });
    gipherService = TestBed.get(GipherService);
    httpTestingController = TestBed.get(HttpTestingController);

  });


  it('should be created', () => {
 //   const service: GipherService = TestBed.get(GipherService);
    expect(GipherService).toBeTruthy();
  });


  it('#addGifToFavourites() should fetch proper response from Http call', () => {
    gipherService.addGifToFavourites(gipher).subscribe(res =>{
      console.log(res);
      expect(res.body).toBe(gipher);
    });

    const url = springEndPoint + "user/" + "test" + "/gipher";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('POST');
    expect(httpMockReq.request.url).toEqual(url);

  });

  it('#getAllGifsFromFavourites() should fetch proper response from Http call', () => {
    gipherService.getAllGifsFromFavourites().subscribe(res =>{
    });

    const url = springEndPoint + "user/" + "test" + "/giphers";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('GET');
    expect(httpMockReq.request.url).toEqual(url);

  });

  it('#deleteGifFromFavourites() should fetch proper response from Http call', () => {
    gipherService.deleteGifFromFavourites(gipher).subscribe(res =>{
    });

    const url = springEndPoint + "user/" + "test" + "/" + gipher.id;
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('DELETE');
    expect(httpMockReq.request.url).toEqual(url);

  });  


  it('#updateComments() should fetch proper response from Http call', () => {
    gipherService.updateComments(gipher).subscribe(res =>{
      console.log(res);
      expect(res.body).toBe(gipher);
    });

    const url = springEndPoint + "user/" + "test" + "/gipher";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('PATCH');
    expect(httpMockReq.request.url).toEqual(url);

  });

  it('#getAllGifsFromRecommended() should fetch proper response from Http call', () => {
    gipherService.getAllGifsFromRecommended().subscribe(res =>{
    });

    const url = recommendEndPoint + "giphers";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('GET');
    expect(httpMockReq.request.url).toEqual(url);

  });
  
  it('#saveUserSearches() should fetch proper response from Http call', () => {
    gipherService.saveUserSearches(searchText).subscribe(res =>{
      console.log(res);
      expect(res.body).toBe(searchText);
    });

    const url = springEndPoint + "user/" + "test" + "/search/" + searchText;
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('POST');
    expect(httpMockReq.request.url).toEqual(url);

  });  

});
