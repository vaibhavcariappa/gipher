package com.stackroute.rabbitmq.domain;

import java.util.List;

public class UserDTO {

    private String username;
    private String email;
    private String password;
    private List<GipherDTO> gifList;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String password, List<GipherDTO> gifList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gifList = gifList;
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

    public List<GipherDTO> getGifList() {
        return gifList;
    }

    public void setGifList(List<GipherDTO> gifList) {
        this.gifList = gifList;
    }
}
