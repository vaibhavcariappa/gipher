package com.stackroute.gipherrecommendersystem.repository;

import com.stackroute.gipherrecommendersystem.domain.Gipher;
import com.stackroute.gipherrecommendersystem.domain.Image;
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
public class GipherRecommenderRepositoryTest {

    @Autowired
    private GipherRecommenderRepository gipherRecommenderRepository;

    private Gipher gipher;

    @Before
    public void setUp() throws Exception {

        Image image  = new Image(1,"http://url.com","large" );
        gipher = new Gipher("gif001","funnyGif1","gifurl.com","internet","1","Nice Gif", 1, image);

    }

    @After
    public void tearDown() throws Exception {
        gipherRecommenderRepository.deleteAll();

    }

    @Test
    public void testSaveGipher() {

        gipherRecommenderRepository.save(gipher);
        Gipher fetchGipher = gipherRecommenderRepository.findByGifId(gipher.getGifId());
        Assert.assertEquals(fetchGipher.getGifId(), gipher.getGifId());
    }


}
