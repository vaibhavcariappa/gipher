package com.stackroute.gipherrecommendersystem.service;

import com.stackroute.gipherrecommendersystem.domain.Gipher;
import com.stackroute.gipherrecommendersystem.exception.GifAlreadyExistsException;
import com.stackroute.gipherrecommendersystem.exception.GifNotFoundException;

import java.util.List;

public interface GipherRecommenderService {

    Gipher saveGifToRecommendedList(Gipher gipher);

    Gipher deleteGifFromRecommendedList(Gipher gipher) throws GifNotFoundException;

    List<Gipher> getAllUserGifsFromRecommendedList() throws Exception;

}
