package com.stackroute.gipherrecommendersystem.service;

import com.stackroute.gipherrecommendersystem.domain.Gipher;
import com.stackroute.gipherrecommendersystem.domain.Image;
import com.stackroute.gipherrecommendersystem.exception.GifNotFoundException;
import com.stackroute.gipherrecommendersystem.repository.GipherRecommenderRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class GipherRecommenderServiceTest {

    @Mock
    private GipherRecommenderRepository gipherRecommenderRepository;

    private Gipher gipher;
    private List<Gipher> gifList;

    @InjectMocks
    GipherRecommenderServiceImpl gipherRecommenderService;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Image image  = new Image(1,"http://url.com","large" );
        gipher = new Gipher("gif001","funnyGif1","gifurl.com","internet","1","Nice Gif", 1,  image);

        gifList = new ArrayList<>();
        gifList.add(gipher);

        gipherRecommenderRepository.deleteAll();

    }

    @After
    public void tearDown() throws Exception {

        gipherRecommenderRepository.deleteAll();
        gifList = null;
        gipher = null;

    }


    @Test
    public void testSaveGifToRecommendedListSuccess()  {

        when(gipherRecommenderRepository.findByGifId(gipher.getGifId())).thenReturn(gipher);
        Gipher fetchedGipher = gipherRecommenderService.saveGifToRecommendedList(gipher);
        Assert.assertEquals(fetchedGipher, gipher);
        verify(gipherRecommenderRepository, times(1)).findByGifId(gipher.getGifId());
        verify(gipherRecommenderRepository, times(1)).save(gipher);
    }


    @Test
    public void testDeleteGifFromRecommendedListSuccess() throws GifNotFoundException {

        when(gipherRecommenderRepository.findByGifId(gipher.getGifId())).thenReturn(gipher);
        Gipher fetchedGipher = gipherRecommenderService.deleteGifFromRecommendedList(gipher);
        Assert.assertEquals(fetchedGipher, gipher);
        verify(gipherRecommenderRepository, times(1)).findByGifId(gipher.getGifId());
        verify(gipherRecommenderRepository, times(1)).delete(gipher);

    }

    @Test
    public void testGetAllUserGifsFromRecommendedListSuccess() throws Exception {

        when(gipherRecommenderRepository.findAll()).thenReturn(gifList);
        List<Gipher> fetchedGifList= gipherRecommenderService.getAllUserGifsFromRecommendedList();
        Assert.assertEquals(fetchedGifList, gifList);
        verify(gipherRecommenderRepository, times(1)).findAll();

    }


}
