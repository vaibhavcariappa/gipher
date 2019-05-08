package com.stackroute.giphermanager.controller;

import com.stackroute.giphermanager.domain.Gipher;
import com.stackroute.giphermanager.domain.User;
import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserAlreadyExistsException;
import com.stackroute.giphermanager.service.UserGifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/giphermanager")
public class UserGifController {

    private UserGifService userGifService;
    private ResponseEntity responseEntity;

    public UserGifController() {
    }

    @Autowired
    public UserGifController(UserGifService userGifService) {
        this.userGifService = userGifService;
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {

        try{
            userGifService.registerUser(user);
            responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @PostMapping("/user/{username}/gipher")
    public ResponseEntity<?> saveUserGifToFavourites(@PathVariable ("username") String username, @RequestBody Gipher gipher) throws GifAlreadyExistsException {

        try{
            User user = userGifService.saveUserGifToFavourites(username, gipher);
            responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (GifAlreadyExistsException e) {
            throw new GifAlreadyExistsException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @DeleteMapping("/user/{username}/{gifId}")
    public ResponseEntity<?> deleteUserGifFromFavourites(@PathVariable ("username") String username, @PathVariable ("gifId") String gifId) throws GifNotFoundException {

        try{
            User user = userGifService.deleteUserGifFromFavourites(username, gifId);
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        } catch (GifNotFoundException e) {
            throw new GifNotFoundException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @PatchMapping("/user/{username}/gipher")
    public ResponseEntity<?> updateCommentForGif(@PathVariable ("username") String username, @RequestBody Gipher gipher) throws GifNotFoundException {

        try{
            userGifService.updateCommentForGif(username, gipher.getGifId(), gipher.getComments());
            responseEntity = new ResponseEntity<>(gipher, HttpStatus.OK);
        } catch (GifNotFoundException e) {
            throw new GifNotFoundException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @PostMapping("/user/{username}/search/{userSearch}")
    public ResponseEntity<?> saveUserSearches(@PathVariable ("username") String username, @PathVariable ("userSearch") String userSearch) throws GifAlreadyExistsException {

        try{
            User user = userGifService.saveUserSearches(username, userSearch);
            responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (GifAlreadyExistsException e) {
            throw new GifAlreadyExistsException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @GetMapping("/user/{username}/giphers")
    public ResponseEntity<?> getAllUserGifsFromFavourites(@PathVariable ("username") String username) {

        try{
            responseEntity = new ResponseEntity<>(userGifService.getAllUserGifsFromFavourites(username), HttpStatus.OK);

        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
