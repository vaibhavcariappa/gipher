package com.stackroute.giphermanager.service;

import com.stackroute.giphermanager.domain.Gipher;
import com.stackroute.giphermanager.domain.User;
import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserAlreadyExistsException;

import java.util.List;


public interface UserGifService {

    User registerUser(User user) throws UserAlreadyExistsException;

    User saveUserGifToFavourites(String username, Gipher gipher) throws GifAlreadyExistsException;

    User deleteUserGifFromFavourites(String username, String gifId) throws GifNotFoundException;

    User updateCommentForGif(String username, String gifId, String comments) throws GifNotFoundException;

    User saveUserSearches(String username, String userSearch) throws Exception;

    List<Gipher> getAllUserGifsFromFavourites(String username) throws Exception;

}
