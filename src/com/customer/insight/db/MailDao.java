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
import java.sql.SQLException;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author TUNGLV
 */
public class MailDao {
    static Logger logger = Logger.getLogger(MailDao.class.getName());


    public static void insert(Mail mail) throws SQLException {
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
                    + "create_date) "
                    + "VALUES (?,?,?,?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, mail.getEmail());
            pst.setString(2, "");
            pst.setString(3, "");
            pst.setString(4, "");
            pst.setString(5, "");
            pst.setString(6, "");
            pst.setDate(7, null);
            pst.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            pst.close();
            c.close();
        }
    }
}
