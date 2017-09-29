/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.facebook.dto;

/**
 *
 * @author TUNGLV
 * Feed: Bai Post tren trang ca nhan hoac fanpage, group
 */
public class Feed {
    private String id;
    private String createTime;
    private String message;
    private String story;


    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
 
    
}
