/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.facebook.usecase;

import com.customer.insight.config.Config;
import com.customer.insight.facebook.dto.User;
import com.customer.insight.http.ResponseUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author TUNGLV
 */
public class UserAction {

    public static void main(String[] args) throws Exception {
        Config cfg = new Config();
        String token = cfg.USER_ACCESS_TOKEN;
        UserAction getUser = new UserAction();
        getUser.getUserByToken(token);
    }

    //lay user theo token
    public User getUserByToken(String token) throws Exception {
        User user = new User();
        try {

            String rsJsonUser = null;
            String urlMe = "https://graph.facebook.com/me?access_token=" + token;
            rsJsonUser = ResponseUtil.sendGet(urlMe);

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(rsJsonUser);
            user.setId(obj.get("id").toString());
            user.setName(obj.get("name").toString());
        } catch (Exception e) {
            throw new Exception("getUserByToken(String token) error: " + e.getMessage());
        }
        return user;
    }

    /**
     * desc: Lay thong tin fanpage user da like va import vao db
     *
     * @param token: ma truy cap nguoi dung
     * @param id: id cua nguoi dung can lay thong tin so luong trang ng dung do da like
     * @return
     * @throws Exception
     */
    public void getPageUserLikes(String token,String id, String groupCode) throws Exception {
        try {
            String rsJsonPageUserLikes = null;
            String urlPageUserLikes = "https://graph.facebook.com/"+id+"?access_token=" + token+ "&fields=likes";
            rsJsonPageUserLikes = ResponseUtil.sendGet(urlPageUserLikes);
        } catch (Exception e) {
            throw new Exception("getPageUserLikes error: " + e.getMessage());
        }
    }

}
