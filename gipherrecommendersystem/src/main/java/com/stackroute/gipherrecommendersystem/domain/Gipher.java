package com.stackroute.gipherrecommendersystem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Gipher {

    @Id
    private String gifId;
    private String gifName;
    private String gifUrl;
    private String source;
    private String rating;
    private String comments;
    private int counter;
    private Image image;

    public Gipher() {
    }

    public Gipher(String gifId, String gifName, String gifUrl, String source, String rating, String comments, int counter, Image image) {
        this.gifId = gifId;
        this.gifName = gifName;
        this.gifUrl = gifUrl;
        this.source = source;
        this.rating = rating;
        this.comments = comments;
        this.counter = counter;
        this.image = image;
    }

    public String getGifId() {
        return gifId;
    }

    public void setGifId(String gifId) {
        this.gifId = gifId;
    }

    public String getGifName() {
        return gifName;
    }

    public void setGifName(String gifName) {
        this.gifName = gifName;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Gipher{" +
                "gifId='" + gifId + '\'' +
                ", gifName='" + gifName + '\'' +
                ", gifUrl='" + gifUrl + '\'' +
                ", source='" + source + '\'' +
                ", rating='" + rating + '\'' +
                ", comments='" + comments + '\'' +
                ", counter=" + counter +
                ", image=" + image +
                '}';
    }
}
