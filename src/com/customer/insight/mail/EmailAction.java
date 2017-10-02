/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.mail;

import com.customer.insight.config.Config;
import com.customer.insight.db.MailDao;
import com.customer.insight.entity.Mail;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author TUNGLV
 */
public class EmailAction {

    public static void main(String[] args) throws SQLException, Exception {
        String from = "lazada.ohaythe@gmail.com";
        String pwd = "123456a@";
        String title = "Combo 3 Quần lót nam xuất Nhật viền màu cotton cao cấp Chikoko";
        String content = "Với phương châm Không chỉ đem đến cho khách hàng những sản phẩm mang phong cách và kiểu dáng độc đáo mà còn giới thiệu đến khách hàng những sản phẩm đạt chất lượng tốt nhất.Các mẫu thiết kế được kiểm duyệt kĩ càng từ khâu chọn chất liệu ,dựng mẫu và hoàn thiện. "
                + "Khách hàng xem thêm sản phẩm tại link: http://bit.ly/2xNNHgO \n" +
"\n" +
"Phiên bản quần Nhật cao cấp nhất,chất mềm nhất,co dãn và thấm hút mồ hôi tốt nhất. Chất liệu mềm mịn, thoáng mát, thấm hút mồ hôi, ôm khít body nhưng vẫn thoải mái.";

        String numMail = Config.NUMBER_MAIL;
        String status = Config.STATUS_MAIL_SEND;
        String statusUpdate = Config.STATUS_MAIL_UPDATE;
        ArrayList<Mail> lst = getListMail(status, numMail);
        for (Mail to : lst) {
            try {
                sendEmail(from, pwd, to.getEmail(), title, content);
                System.out.println("tunglv gui toi mail" + to + " thanh cong");
                //update status
                to.setStatus(Integer.parseInt(statusUpdate));
                MailDao.updateMail(to);
            } catch (Exception e) {
                System.out.println("tunglv gui toi mail: " + to + " bi loi" + e.getMessage());
            }
        }
    }

    //mailSend: email gui
    //passwordMailSend: mat khau cua email gui
    //mailRecipient: dia chi email nhan
    //title: tieu de mail
    //content: noi dung mail
    public static void sendEmail(String mailSend, String passwordMailSend, String mailRecipient, String title, String content) throws MessagingException {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    //chu y neu gap loi nay thi phai bat bao mat tren tai khoan mail google cho phep ung dung it bao mat hon Erreur d'envoi, cause: javax.mail.AuthenticationFailedException: 535-5.7.8 Username and Password not accepted. Learn more at
                    return new PasswordAuthentication(mailSend, passwordMailSend);
                }
            });

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(mailSend));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailRecipient, false));
            msg.setSubject(title);
            msg.setText(content);
            msg.setSentDate(new Date());
            Transport.send(msg);
            //System.out.println("Message sent.");
        } catch (MessagingException e) {
            throw new MessagingException(e.getMessage());
        }
    }

    //lay danh sach mail trong DB: TBL_MAIL 
    //(gioi han so luong ban ghi lay ve theo cau hinh NUMBER_MAIL)
    //select  *   from TBL_MAIL   limit 5 ;
    private static ArrayList<Mail> getListMail(String status, String numMail) throws SQLException, Exception {
        ArrayList<Mail> lstMail = null;
        lstMail = MailDao.getListMail(status, numMail);
        return lstMail;

    }

    //kiem tra tai khoan mail da ton tai chua
    //fasle:chua ton tai true:da ton tai
    public static boolean checkMailExisted(String mail) throws SQLException, Exception {
        boolean check = false;
        Mail m = MailDao.getByEmail(mail);
        if (m != null) {
            return true;
        }
        return check;
    }

    private static String updateMail(Mail mail) throws SQLException {
        String result = "updateMail Success";
        try {
            MailDao.updateMail(mail);
        } catch (Exception e) {
            result = "updateMail Error";
        }
        return result;

    }
}
