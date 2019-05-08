package com.stackroute.gipherrecommendersystem.controller;

import com.stackroute.gipherrecommendersystem.domain.Gipher;
import com.stackroute.gipherrecommendersystem.exception.GifNotFoundException;
import com.stackroute.gipherrecommendersystem.service.GipherRecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/gipherrecommendersystem")
public class GipherRecommenderController {

    private ResponseEntity responseEntity;
    private GipherRecommenderService gipherRecommenderService;

    @Value("${errorString}")
    private String errorString;

    @Autowired
    public GipherRecommenderController(GipherRecommenderService gipherRecommenderService) {
        this.gipherRecommenderService = gipherRecommenderService;
    }

    @PostMapping("/save")
    public ResponseEntity saveGifToRecommendedList(@RequestBody Gipher gipher) throws Exception{

        try {
            gipherRecommenderService.saveGifToRecommendedList(gipher);
            return responseEntity = new ResponseEntity(gipher, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(errorString, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    @PostMapping("/delete")
    public ResponseEntity deleteGifFromRecommendedList(@RequestBody Gipher gipher) throws GifNotFoundException {

        try{
            gipherRecommenderService.deleteGifFromRecommendedList(gipher);
            responseEntity = new ResponseEntity<>(gipher, HttpStatus.OK);
        } catch (GifNotFoundException e) {
            throw new GifNotFoundException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(errorString, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    @GetMapping("/giphers")
    public ResponseEntity<?> getAllUserGifsFromFavourites() {

        try{
            responseEntity = new ResponseEntity<>(gipherRecommenderService.getAllUserGifsFromRecommendedList(), HttpStatus.OK);

        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(errorString, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


}
