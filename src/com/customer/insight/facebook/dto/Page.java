/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.facebook.dto;

/**
 *
 * @author TUNGLV
 * Page: Trang ca nhan, fanpage
 */
public class Page {
    //ten trang
    private String name;
    private String id;
    //thoi gian thich trang
    private String creatTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
    
    
}
