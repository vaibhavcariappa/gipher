package com.stackroute.gipherrecommendersystem.service;

import com.stackroute.gipherrecommendersystem.domain.Gipher;
import com.stackroute.gipherrecommendersystem.exception.GifAlreadyExistsException;
import com.stackroute.gipherrecommendersystem.exception.GifNotFoundException;
import com.stackroute.gipherrecommendersystem.repository.GipherRecommenderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GipherRecommenderServiceImpl implements GipherRecommenderService {
    private GipherRecommenderRepository gipherRecommenderRepository;

    public GipherRecommenderServiceImpl(GipherRecommenderRepository gipherRecommenderRepository) {
        this.gipherRecommenderRepository = gipherRecommenderRepository;
    }

    @Override
    public Gipher saveGifToRecommendedList(Gipher gipher) {

        int counter = 1;
        Gipher gifObj = gipherRecommenderRepository.findByGifId(gipher.getGifId());

        if(gifObj != null) {
            counter = gifObj.getCounter() + 1;
        }
        gipher.setCounter(counter);
        gipherRecommenderRepository.save(gipher);

        return gifObj;
    }

    @Override
    public Gipher deleteGifFromRecommendedList(Gipher gipher) throws GifNotFoundException {

        int counter = 0;
        Gipher gifObj = gipherRecommenderRepository.findByGifId(gipher.getGifId());

        if(gifObj != null) {
            counter = gifObj.getCounter() - 1;

            if(counter <= 0) {
                gipherRecommenderRepository.delete(gifObj);
            } else {
                gifObj.setCounter(counter);
                gipherRecommenderRepository.save(gifObj);
            }
        } else {
            throw new GifNotFoundException();
        }

        return gifObj;
    }


    @Override
    public List<Gipher> getAllUserGifsFromRecommendedList() throws Exception {
        return gipherRecommenderRepository.findAll();
    }
}
