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
    //truoc khi gui mail đăng nhập vào mail gửi chèn chữ ký là ảnh của sản phẩm
    public static void main(String[] args) throws SQLException, Exception {
//        String from = "lazada.ohaythe@gmail.com";
//        String pwd = "123456a@";
        String from = "m1.sonlv95@gmail.com";
        String pwd = "123456a@";
//        String from = "m2.sonlv95@gmail.com";
//        String pwd = "123456a@";
        String title = "Mũ bảo hiểm 1/2 đen + kính phi công + kính chống tia uv (Đen)";
        String content = "Mũ bảo hiểm là 1 trong những phụ kiện rất cần thiết cho tất cả những người lái nói chung và cho các rider scooterist nói riêng . Không chỉ là thứ bảo vệ an toàn cho người lái xe mà nó còn là 1 phong cách , thể hiện cái \" gu \" của mỗi người cũng như chiếc xe mà bạn đang sở hữu !\n"
                + "\n"
                + "Chúng Tôi nghĩ rằng ko dưới 1 lần các bạn đã từng lưỡng lự trước cửa hàng mũ với vô vàn chủng loại khác nhau và băn khoăn ko biết chọn cái nào cho đúng sở thích và hợp \"gu\" của mình , vì thế chúng tôi mạo muội giới thiệu với các bạn 1 loại mũ bảo hiểm rất được ưa chuộng từ những năm 50,60 của thập kỉ trước cho đến tận bây giờ đấy là loại mũ 3/4 đầu , hở cằm với mái che đơn giản mà thường được gọi là \" Classic Retro Helmet \" (CRH)"
                + "\n"
                + "CLICK NGAY VÀ LUÔN NẾU BẠN ĐANG CẦN MUA MỘT SẢN PHẨM VỪA RẺ LẠI VỪA CHẤT NHƯ VẬY TẠI LINK:"
                + "\n"
                + " http://bit.ly/2yHELJ7"
                + "\n"
                + " Sâu kiu"
                + "\n"
                + "https://www.facebook.com/khuyenmai.sale.lazada/";
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
                if (e.getMessage() != null && e.getMessage() != "" && e.getMessage().contains("550 5.4.5 Daily user sending quota exceeded")) {
                    throw new Exception("Email da gui qua so luong cho phep trong ngay");
                }
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
    //com.sun.mail.smtp.SMTPSendFailedException: 550 5.4.5 Daily user sending quota exceeded. r12sm16585631pfd.187 - gsmtp
    //desc: loi mot ngay gui vuot qua so luong mail cho phep
}
