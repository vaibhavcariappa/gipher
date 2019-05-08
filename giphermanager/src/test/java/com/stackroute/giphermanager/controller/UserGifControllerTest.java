package com.stackroute.giphermanager.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.giphermanager.domain.Gipher;
import com.stackroute.giphermanager.domain.Image;
import com.stackroute.giphermanager.domain.User;
import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.UserAlreadyExistsException;
import com.stackroute.giphermanager.service.UserGifService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserGifController.class)
public class UserGifControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserGifService userGifService;

    private User user;
    private Gipher gipher;
    private Image image;
    private List<Gipher> gifList;
    private List<String> searchList;

    @Before
    public void setUp() throws Exception {

        gifList = new ArrayList<>();

        image  = new Image(1,"http://url.com","large" );
        gipher = new Gipher("gif001","funnyGif1","gifurl.com","internet","1","Nice Gif", image);
        gifList.add(gipher);


        image  = new Image(2,"http://url.com","large" );
        gipher = new Gipher("gif002","funnyGif2","gifurl.com","internet","1","Nice Gif", image);
        gifList.add(gipher);

        user = new User("John","john@example.com", gifList);

        searchList = new ArrayList<>();
        searchList.add("searchGif");
        user.setSearches(searchList);
    }


    @After
    public void tearDown() throws Exception {
        image = null;
        gipher = null;
        gifList = null;
        user = null;
    }


    @Test
    public void testRegisterUserSuccess() throws Exception{

        when(userGifService.registerUser(any())).thenReturn(user);
        mockMvc.perform(post("/api/v1/giphermanager/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(user)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(userGifService, times(1)).registerUser(any());
    }

    @Test
    public void testRegisterUserFailure() throws Exception{

        when(userGifService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/giphermanager/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(user)))
                .andExpect(status().isConflict())
                .andDo(print());

        verify(userGifService, times(1)).registerUser(any());
    }

    @Test
    public void testSaveUserGifToFavouritesSuccess() throws Exception{

        when(userGifService.saveUserGifToFavourites(eq(user.getUsername()), any())).thenReturn(user);
        mockMvc.perform(post("/api/v1/giphermanager/user/{username}/gipher", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(gipher)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(userGifService, times(1)).saveUserGifToFavourites(eq(user.getUsername()), any());
    }


    @Test
    public void testSaveUserGifToFavouritesFailure() throws Exception{

        when(userGifService.saveUserGifToFavourites(eq(user.getUsername()), any())).thenThrow(GifAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/giphermanager/user/{username}/gipher", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(gipher)))
                .andExpect(status().isConflict())
                .andDo(print());

        verify(userGifService, times(1)).saveUserGifToFavourites(eq(user.getUsername()), any());
    }


    @Test
    public void testUpdateCommentForGifSuccess() throws Exception{

        when(userGifService.updateCommentForGif(user.getUsername(), gipher.getGifId(), gipher.getComments())).thenReturn(user);
        mockMvc.perform(patch("/api/v1/giphermanager/user/{username}/gipher", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(gipher)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userGifService, times(1)).updateCommentForGif(user.getUsername(), gipher.getGifId(), gipher.getComments());
    }


    @Test
    public void testDeleteUserGifFromFavouritesSuccess() throws Exception{

        when(userGifService.deleteUserGifFromFavourites(user.getUsername(), gipher.getGifId())).thenReturn(user);
        mockMvc.perform(delete("/api/v1/giphermanager/user/{username}/{gifId}", user.getUsername(), gipher.getGifId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(gipher)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userGifService, times(1)).deleteUserGifFromFavourites(user.getUsername(), gipher.getGifId());
    }

    @Test
    public void testSaveUserSearchesSuccess() throws Exception{

        String search = "newSearchGif";
        when(userGifService.saveUserSearches(user.getUsername(), search)).thenReturn(user);
        mockMvc.perform(post("/api/v1/giphermanager/user/{username}/search/{userSearch}", user.getUsername(), search)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(search)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(userGifService, times(1)).saveUserSearches(user.getUsername(), search);
    }

    @Test
    public void testGetAllUserGifsFromFavouritesSuccess() throws Exception{

        when(userGifService.getAllUserGifsFromFavourites(user.getUsername())).thenReturn(gifList);
        mockMvc.perform(get("/api/v1/giphermanager//user/{username}/giphers", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(gipher)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userGifService, times(1)).getAllUserGifsFromFavourites(user.getUsername());
    }


    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try{
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "Json Processing error";
        }
        return result;
    }


}
