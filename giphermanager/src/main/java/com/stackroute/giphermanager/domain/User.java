package com.stackroute.giphermanager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {

    @Id
    private String username;
    private String email;
    private String password;
    private List<Gipher> gifList;
    private List<String> searches;

    public User() {
    }

    public User(String username, String email, List<Gipher> gifList) {
        this.username = username;
        this.email = email;
        this.gifList = gifList;
    }

    public User(String username, String email, String password, List<Gipher> gifList, List<String> searches) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gifList = gifList;
        this.searches = searches;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Gipher> getGifList() {
        return gifList;
    }

    public void setGifList(List<Gipher> gifList) {
        this.gifList = gifList;
    }

    public List<String> getSearches() {
        return searches;
    }

    public void setSearches(List<String> searches) {
        this.searches = searches;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gifList=" + gifList +
                ", searches=" + searches +
                '}';
    }
}
