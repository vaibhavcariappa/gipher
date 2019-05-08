package com.stackroute.giphermanager.repository;

import com.stackroute.giphermanager.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserGifRepository extends MongoRepository<User, String>  {

    public User findByUsername(String username);

}
