package com.stackroute.giphermanager.repository;

import com.stackroute.giphermanager.domain.Gipher;
import com.stackroute.giphermanager.domain.Image;
import com.stackroute.giphermanager.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserGifRepositoryTest {

    @Autowired
    private UserGifRepository userGifRepository;

    private Gipher gipher;
    private User user;

    @Before
    public void setUp() throws Exception {

        Image image  = new Image(1,"http://url.com","large" );

        gipher = new Gipher("gif001","funnyGif1","gifurl.com","internet","1","Nice Gif", image);

        List<Gipher> gifList = new ArrayList<>();
        gifList.add(gipher);
        user = new User("John","john@example.com", gifList);
    }

    @After
    public void tearDown() throws Exception {
        userGifRepository.deleteAll();

    }

    @Test
    public void testSaveUserTrack() {

        userGifRepository.save(user);
        User fetchUser = userGifRepository.findByUsername(user.getUsername());
        List <Gipher> trackList = fetchUser.getGifList();
        Assert.assertEquals(trackList.get(0).getGifId(), user.getGifList().get(0).getGifId());
    }

}
