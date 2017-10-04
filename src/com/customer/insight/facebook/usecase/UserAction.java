/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.facebook.usecase;

import com.customer.insight.config.Config;
import com.customer.insight.facebook.dto.User;
import com.customer.insight.http.ResponseUtil;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Conversation;
import com.restfb.types.Message;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.SendResponse;
import java.util.List;
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
        User u = getUser.getUserByToken(token);

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
     * desc: Lay thong tin user da like
     *
     * @param token: ma truy cap nguoi dung
     * @param id: id cua nguoi dung can lay thong tin so luong trang ng dung do
     * da like
     * @return
     * @throws Exception
     */
    public void getPageUserLikes(String token, String id) throws Exception {
        try {
            String rsJsonPageUserLikes = null;
            String urlPageUserLikes = "https://graph.facebook.com/" + id + "/likes?access_token=" + token;
            rsJsonPageUserLikes = ResponseUtil.sendGet(urlPageUserLikes);
            System.out.println("thuc hien thanh cong");
        } catch (Exception e) {
            throw new Exception("getPageUserLikes error: " + e.getMessage());
        }
    }

}
