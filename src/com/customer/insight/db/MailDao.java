/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.db;

import com.customer.insight.config.Config;
import com.customer.insight.entity.Mail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author TUNGLV
 */
public class MailDao {

    static Logger logger = Logger.getLogger(MailDao.class.getName());

    public static void insert(Mail mail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + Mail.TABLE_NAME
                    + "(email,"
                    + "owner,"
                    + "birth_day,"
                    + "address,"
                    + "mobile,"
                    + "note,"
                    + "create_date,"
                    + "status) "
                    + "VALUES (?,?,?,?,?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, mail.getEmail());
            pst.setString(2, "");
            pst.setString(3, "");
            pst.setString(4, "");
            pst.setString(5, "");
            pst.setString(6, "");
            pst.setDate(7, null);
            pst.setInt(8, Mail.STATUS_INSERT);
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            pst.close();
            c.close();
        }
    }

    public static Mail getByEmail(String email) throws SQLException, Exception {
        Mail m = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + Mail.TABLE_NAME
                    + " WHERE "
                    + " EMAIL = ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email");
                m = new Mail();
                m.setId(id);
                m.setEmail(mail);

            }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
        return m;

    }

    public static ArrayList<Mail> getListMail(String status, String limit) throws SQLException, Exception {
        ArrayList<Mail> mails = new ArrayList<>();
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + Mail.TABLE_NAME
                    + " WHERE STATUS = ? LIMIT ?; ";
            pst = c.prepareStatement(query);
             pst.setString(1, status);
            pst.setString(2, limit);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email");
                Mail m = new Mail();
                m.setId(id);
                m.setEmail(mail);
                mails.add(m);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
        return mails;

    }
    //tam thoi chi update status
    public static void updateMail(Mail mail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "UPDATE  " + Mail.TABLE_NAME
                    + " SET STATUS = ? WHERE ID= ?; ";
            pst = c.prepareStatement(query);
             pst.setInt(1, mail.getStatus());
            pst.setInt(2, mail.getId());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
    
    }
}
