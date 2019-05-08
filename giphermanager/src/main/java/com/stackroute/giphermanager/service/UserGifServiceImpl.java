package com.stackroute.giphermanager.service;

import com.stackroute.giphermanager.config.Producer;
import com.stackroute.giphermanager.domain.Gipher;
import com.stackroute.giphermanager.domain.User;
import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserAlreadyExistsException;
import com.stackroute.giphermanager.repository.UserGifRepository;
import com.stackroute.rabbitmq.domain.GipherDTO;
import com.stackroute.rabbitmq.domain.ImageDTO;
import com.stackroute.rabbitmq.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGifServiceImpl implements UserGifService {

    private UserGifRepository userGifRepository;
    private Producer producer;

    @Autowired
    public UserGifServiceImpl(UserGifRepository userGifRepository, Producer producer) {
        this.userGifRepository = userGifRepository;
        this.producer = producer;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());

        User fetchedUserObj = userGifRepository.findByUsername(user.getUsername());
        if(fetchedUserObj != null) {
            throw new UserAlreadyExistsException();
        } else {
            userGifRepository.save(user);
            producer.sendMessageToRabbitMq(userDTO);
        }
        return user;

    }

    @Override
    public User saveUserGifToFavourites(String username, Gipher gipher) throws GifAlreadyExistsException {

        User fetchUser = userGifRepository.findByUsername(username);
        List<Gipher> fetchGifs = fetchUser.getGifList();

        if(fetchGifs != null) {
            for (Gipher gifObj: fetchGifs) {
                if(gifObj.getGifId().equals(gipher.getGifId())) {
                    throw new GifAlreadyExistsException();
                }
            }
            fetchGifs.add(gipher);
            fetchUser.setGifList(fetchGifs);
            userGifRepository.save(fetchUser);

        } else {

            fetchGifs = new ArrayList<Gipher>();
            fetchGifs.add(gipher);
            fetchUser.setGifList(fetchGifs);
            userGifRepository.save(fetchUser);

        }

        ImageDTO imageDTO = new ImageDTO(gipher.getImage().getImageId(),
                gipher.getImage().getImageUrl(), gipher.getImage().getImageSpec());

        GipherDTO gipherDTO = new GipherDTO(gipher.getGifId(), gipher.getGifName(), gipher.getGifUrl(), gipher.getSource(),
                gipher.getRating(), gipher.getComments(), imageDTO);


        producer.sendAddRecommendedGifToRabbitMq(gipherDTO);

        return fetchUser;

    }

    @Override
    public User deleteUserGifFromFavourites(String username, String gifId) throws GifNotFoundException {

        User fetchUser = userGifRepository.findByUsername(username);
        List<Gipher> fetchGifs = fetchUser.getGifList();

        if(fetchGifs.size() > 0) {
            for(int i=0; i < fetchGifs.size(); i++) {

                if(gifId.equals(fetchGifs.get(i).getGifId())) {

                    Gipher fetchGipher = fetchGifs.get(i);
                    ImageDTO imageDTO = new ImageDTO(fetchGipher.getImage().getImageId(),
                            fetchGipher.getImage().getImageUrl(), fetchGipher.getImage().getImageSpec());

                    GipherDTO gipherDTO = new GipherDTO(fetchGipher.getGifId(), fetchGipher.getGifName(), fetchGipher.getGifUrl(),
                            fetchGipher.getSource(), fetchGipher.getRating(), fetchGipher.getComments(), imageDTO);

                    fetchGifs.remove(i);
                    fetchUser.setGifList(fetchGifs);
                    userGifRepository.save(fetchUser);
                    producer.sendDelRecommendedGifToRabbitMq(gipherDTO);
                    break;
                }

            }
        } else {
            throw new GifNotFoundException();
        }

        return fetchUser;
    }

    @Override
    public User updateCommentForGif(String username, String gifId, String comments) throws GifNotFoundException {

        User fetchUser = userGifRepository.findByUsername(username);
        List<Gipher> fetchGifs = fetchUser.getGifList();

        if(fetchGifs.size() > 0) {
            for(int i=0; i < fetchGifs.size(); i++) {

                if(gifId.equals(fetchGifs.get(i).getGifId())) {
                    fetchGifs.get(i).setComments(comments);
                    userGifRepository.save(fetchUser);
                    break;
                }

            }
        } else {
            throw new GifNotFoundException();
        }

        return fetchUser;
    }

    @Override
    public User saveUserSearches(String username, String userSearch) throws Exception {

        User fetchUser = userGifRepository.findByUsername(username);
        if(userSearch == null || "".equals(userSearch)) {
            return fetchUser;
        }

        List<String> fetchSearches = fetchUser.getSearches();

        if(fetchSearches != null) {
            for (String searchString: fetchSearches) {
                if(searchString.equals(userSearch)) {
                    return fetchUser;
                }
            }
            fetchSearches.add(0,userSearch);
            fetchUser.setSearches(fetchSearches);
            userGifRepository.save(fetchUser);

        } else {

            fetchSearches = new ArrayList<String>();
            fetchSearches.add(userSearch);
            fetchUser.setSearches(fetchSearches);
            userGifRepository.save(fetchUser);

        }

        return fetchUser;
    }

    @Override
    public List<Gipher> getAllUserGifsFromFavourites(String username) throws Exception {
        User user = userGifRepository.findByUsername(username);
        return user.getGifList();
    }
}
