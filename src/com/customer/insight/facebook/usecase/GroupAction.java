/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.facebook.usecase;

import com.customer.insight.config.Config;
import com.customer.insight.facebook.dto.Feed;
import com.customer.insight.facebook.dto.Group;
import com.customer.insight.http.ResponseUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author tunglv hoat dong lien quan den nhom
 */
public class GroupAction {

    //lay thong tin cua nhom da tham gia
    //idGroup: id nhom 1477039208979448
    public Group getGroupInfo(String token, String idGroup) throws Exception {
        Group group = new Group();
        String url = "https://graph.facebook.com/v2.10/" + idGroup + "?access_token=" + token;
        String rsJson = ResponseUtil.sendGet(url);
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(rsJson);
        group.setId(obj.get("id").toString());
        group.setName(obj.get("name").toString());
        return group;
    }

    //post bai len group da tham gia
    //token: ma xac thuc nguoi dung
    //id cua nhom
    //noi dung bai post
    public void postFeedOnGroup(String token, String idGroup, String msg) throws Exception {
        String url = "https://graph.facebook.com/v2.10/" + idGroup + "/feed?message=" + msg + "&access_token=" + token;
        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.sendPost(url);
    }

    //lay danh sach bai dang cua nhom tu ngay truyen vao den hien tai
    //truyen vao fromDate, 
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //Date from = sdf.parse("2017-09-28");
    //token : ma truy nhap cua user
    public ArrayList<Feed> getFeedOfGroup(String token, String idGroup, Date fromDate) throws Exception {
        JSONParser parser = null;
        String url = "https://graph.facebook.com/" + idGroup + "/feed?access_token=" + token;
        String rs = ResponseUtil.sendGet(url);
        parser = new JSONParser();
        JSONObject objFeed = (JSONObject) parser.parse(rs);
        JSONArray data = (JSONArray) objFeed.get("data");
        ArrayList<Feed> lstFeed = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject feed = (JSONObject) data.get(i);
            String update_time = feed.get("updated_time").toString();
            //2017-09-27T08:29:04+0000
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date updateTime = sdf.parse(update_time);

            if (updateTime.after(fromDate) || updateTime.equals(fromDate)) {
                Feed f = new Feed();
                Object keyMessage = feed.get("message");
                f.setId(feed.get("id").toString());
                f.setUpdateTime(update_time);
                if (keyMessage != null) {
                    f.setMessage(feed.get("message").toString());
                }
                lstFeed.add(f);
            }

        }
        return lstFeed;
    }

    //loi goi http
    //403: la ko dung url
    //400: khong co quyen post
    public static void main(String[] args) throws Exception {
        Config cfg = new Config();
        String token = cfg.USER_ACCESS_TOKEN;
        GroupAction action = new GroupAction();
        //id group 1477039208979448

        

        System.out.println("thuc hien thanh cong");

    }

}
