package com.stackroute.gipherrecommendersystem.config;


import com.stackroute.gipherrecommendersystem.domain.Gipher;
import com.stackroute.gipherrecommendersystem.domain.Image;
import com.stackroute.gipherrecommendersystem.exception.GifNotFoundException;
import com.stackroute.gipherrecommendersystem.service.GipherRecommenderService;
import com.stackroute.rabbitmq.GipherDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private GipherRecommenderService gipherRecommenderService;

    @RabbitListener(queues = "user_addgif_queue")
    public void getSaveGipherDtoFromRabbitMq(GipherDTO gipherDTO) {

        Image image = new Image(gipherDTO.getImage().getImageId(),
                gipherDTO.getImage().getImageUrl(), gipherDTO.getImage().getImageSpec());

        Gipher gipher = new Gipher(gipherDTO.getGifId(), gipherDTO.getGifName(), gipherDTO.getGifUrl(), gipherDTO.getSource(),
                gipherDTO.getRating(), gipherDTO.getComments(), gipherDTO.getCounter(), image);


        gipherRecommenderService.saveGifToRecommendedList(gipher);

    }

    @RabbitListener(queues = "user_delgif_queue")
    public void getDeleteGipherDtoFromRabbitMq(GipherDTO gipherDTO) {

        Image image = new Image(gipherDTO.getImage().getImageId(),
                gipherDTO.getImage().getImageUrl(), gipherDTO.getImage().getImageSpec());

        Gipher gipher = new Gipher(gipherDTO.getGifId(), gipherDTO.getGifName(), gipherDTO.getGifUrl(), gipherDTO.getSource(),
                gipherDTO.getRating(), gipherDTO.getComments(), gipherDTO.getCounter(), image);


        try {
            gipherRecommenderService.deleteGifFromRecommendedList(gipher);
        } catch (GifNotFoundException e) {

        } catch (Exception e) {

        }

    }
}
