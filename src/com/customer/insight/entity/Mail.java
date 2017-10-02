/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.entity;

import java.util.Date;

/**
 *
 * @author TUNGLV
 * luu thong tin tai khoan mail
 */
public class Mail {
    public static final String TABLE_NAME = "TBL_MAIL";
    //id
    String id;
    //dia chi email
    String email;
    //cua ai
    String owner;
    //gioi tinh male, female
    String gender;
    //ngay sinh
    Date birthDay;
    //dia chi
    String address;
    //so dien thoai
    String mobile;
    //nhung truong sau nay se them vao ngoai thiet ke co san
    //cac truong ngan cach nhau bang ky tu |
    String note;
    //ngay insert vao csdl
    Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    
  
}
