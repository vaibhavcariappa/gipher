package com.stackroute.giphermanager.service;

import com.stackroute.giphermanager.config.Producer;
import com.stackroute.giphermanager.domain.Gipher;
import com.stackroute.giphermanager.domain.Image;
import com.stackroute.giphermanager.domain.User;
import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserAlreadyExistsException;
import com.stackroute.giphermanager.repository.UserGifRepository;
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

public class UserGifServiceTest {


    @Mock
    private UserGifRepository userGifRepository;
    @Mock
    private Producer producer;

    private User user;
    private Gipher gipher;
    private List<Gipher> gifList;
    private List<String> searchList;

    @InjectMocks
    UserGifServiceImpl userGifService;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Image image  = new Image(1,"http://url.com","large" );
        gipher = new Gipher("gif001","funnyGif1","gifurl.com","internet","1","Nice Gif", image);

        gifList = new ArrayList<>();
        gifList.add(gipher);
        user = new User("John","john@example.com", gifList);

        searchList = new ArrayList<>();
        searchList.add("searchGif");
        user.setSearches(searchList);

        userGifRepository.deleteAll();

    }

    @After
    public void tearDown() throws Exception {

        userGifRepository.deleteAll();
        gifList = null;
        gipher = null;
        user = null;

    }


    @Test
    public void testRegisterUserSuccess() throws UserAlreadyExistsException {

        when(userGifRepository.save(user)).thenReturn(user);
        User fetchedUser = userGifService.registerUser(user);
        Assert.assertEquals(fetchedUser.getUsername(), user.getUsername());
        verify(userGifRepository, times(1)).findByUsername(user.getUsername());
        verify(userGifRepository, times(1)).save(user);
    }

    @Test
    public void testSaveUserGifToFavouritesSuccess() throws GifAlreadyExistsException {
        user = new User("Mary","mary@example.com", null);
        when(userGifRepository.findByUsername(user.getUsername())).thenReturn(user);
        User fetchedUser = userGifService.saveUserGifToFavourites(user.getUsername(), gipher);
        Assert.assertEquals(fetchedUser, user);
        verify(userGifRepository, times(1)).findByUsername(user.getUsername());
        verify(userGifRepository, times(1)).save(user);
    }


    @Test
    public void testDeleteUserGifFromFavouritesSuccess() throws GifNotFoundException {

        when(userGifRepository.findByUsername(user.getUsername())).thenReturn(user);
        User fetchedUser = userGifService.deleteUserGifFromFavourites(user.getUsername(), gipher.getGifId());
        Assert.assertEquals(fetchedUser, user);
        verify(userGifRepository, times(1)).findByUsername(user.getUsername());
        verify(userGifRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateCommentForGifSuccess() throws GifNotFoundException {

        when(userGifRepository.findByUsername(user.getUsername())).thenReturn(user);
        User fetchedUser = userGifService.updateCommentForGif(user.getUsername(), gipher.getGifId(), "New Comments");
        Assert.assertEquals(fetchedUser.getGifList().get(0).getComments(), "New Comments");
        verify(userGifRepository, times(1)).findByUsername(user.getUsername());
        verify(userGifRepository, times(1)).save(user);
    }


    @Test
    public void testSaveUserSearchesSuccess() throws Exception {

        when(userGifRepository.findByUsername(user.getUsername())).thenReturn(user);
        User fetchedUser = userGifService.saveUserSearches(user.getUsername(), "newSearchGif");
        Assert.assertEquals(fetchedUser.getSearches().get(0).toString(), "newSearchGif");
        verify(userGifRepository, times(1)).findByUsername(user.getUsername());
        verify(userGifRepository, times(1)).save(user);
    }

    @Test
    public void testGetAllUserGifsFromFavourites() throws Exception {

        when(userGifRepository.findByUsername(user.getUsername())).thenReturn(user);
        List<Gipher> fetchedGifList= userGifService.getAllUserGifsFromFavourites(user.getUsername());
        Assert.assertEquals(fetchedGifList, gifList);
        verify(userGifRepository, times(1)).findByUsername(user.getUsername());

    }
}
