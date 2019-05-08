package com.stackroute.gipherrecommendersystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.gipherrecommendersystem.domain.Gipher;
import com.stackroute.gipherrecommendersystem.domain.Image;
import com.stackroute.gipherrecommendersystem.service.GipherRecommenderService;
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
@WebMvcTest(GipherRecommenderController.class)
public class GipherRecommenderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GipherRecommenderService gipherRecommenderService;

    private Gipher gipher;
    private Image image;
    private List<Gipher> gifList;

    @Before
    public void setUp() throws Exception {

        gifList = new ArrayList<>();

        image  = new Image(1,"http://url.com","large" );
        gipher = new Gipher("gif001","funnyGif1","gifurl.com","internet","1","Nice Gif", 1, image);
        gifList.add(gipher);


        image  = new Image(2,"http://url.com","large" );
        gipher = new Gipher("gif002","funnyGif2","gifurl.com","internet","1","Nice Gif", 1, image);
        gifList.add(gipher);

    }


    @After
    public void tearDown() throws Exception {
        image = null;
        gipher = null;
        gifList = null;
    }



    @Test
    public void testSaveGifToRecommendedListSuccess() throws Exception{

        when(gipherRecommenderService.saveGifToRecommendedList(any())).thenReturn(gipher);
        mockMvc.perform(post("/api/v1/gipherrecommendersystem/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(gipher)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(gipherRecommenderService, times(1)).saveGifToRecommendedList(any());
    }


    @Test
    public void testDeleteGifFromRecommendedListSuccess() throws Exception{

        when(gipherRecommenderService.deleteGifFromRecommendedList(any())).thenReturn(gipher);
        mockMvc.perform(post("/api/v1/gipherrecommendersystem/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(gipher)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(gipherRecommenderService, times(1)).deleteGifFromRecommendedList(any());
    }

    @Test
    public void testGetAllUserGifsFromRecommendedListSuccess() throws Exception{

        when(gipherRecommenderService.getAllUserGifsFromRecommendedList()).thenReturn(gifList);
        mockMvc.perform(get("/api/v1/gipherrecommendersystem/giphers"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(gipherRecommenderService, times(1)).getAllUserGifsFromRecommendedList();

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
