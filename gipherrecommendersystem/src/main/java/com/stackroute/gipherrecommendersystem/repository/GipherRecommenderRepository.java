package com.stackroute.gipherrecommendersystem.repository;

import com.stackroute.gipherrecommendersystem.domain.Gipher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GipherRecommenderRepository extends MongoRepository<Gipher, String>  {

    public Gipher findByGifId(String gifId);

}
