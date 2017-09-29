/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.facebook.dto;

import java.util.List;

/**
 *
 * @author TUNGLV
 */
public class User {
    private String id;
    private String name;
    private String birthDay;
    private List<Feed> listFeed;

    public List<Feed> getListFeed() {
        return listFeed;
    }

    public void setListFeed(List<Feed> listFeed) {
        this.listFeed = listFeed;
    }
    
    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
