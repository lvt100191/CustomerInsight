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
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author TUNGLV
 */
public class EmailAction {

    //truong hop gui den nhieu email va update bang tbl_mail
    //truoc khi gui mail len trang https://wordtohtml.net/ soan html online để trình bày sau đó paste noi dung vao content
    //thay hinh  anh img trong package images
    public static void sendMultiEmail() throws Exception {
        String from = "lazada.ohaythe@gmail.com";
        String pwd = "123456a@";
//        String from = "m1.sonlv95@gmail.com";
//        String pwd = "123456a@";
//        String from = "m2.sonlv95@gmail.com";
//        String pwd = "123456a@";
        String title = "Combo 3 Quần lót nam xuất Nhật viền màu cotton cao cấp Chikoko ";
        String content = "<p style=\"text-align: justify;\">Với phương ch&acirc;m Kh&ocirc;ng chỉ đem đến cho kh&aacute;ch h&agrave;ng những sản phẩm mang phong c&aacute;ch v&agrave; kiểu d&aacute;ng độc đ&aacute;o m&agrave; c&ograve;n giới thiệu đến kh&aacute;ch h&agrave;ng những sản phẩm đạt chất lượng tốt nhất.C&aacute;c mẫu thiết kế được kiểm duyệt kĩ c&agrave;ng từ kh&acirc;u chọn chất liệu ,dựng mẫu v&agrave; ho&agrave;n thiện.</p>\n"
                + "<p style=\"text-align: justify;\">Phi&ecirc;n bản quần Nhật cao cấp nhất,chất mềm nhất,co d&atilde;n v&agrave; thấm h&uacute;t mồ h&ocirc;i tốt nhất. Chất liệu mềm mịn, tho&aacute;ng m&aacute;t, thấm h&uacute;t mồ h&ocirc;i, &ocirc;m kh&iacute;t body nhưng vẫn thoải m&aacute;i. Quần c&oacute; kiểu d&aacute;ng thời trang, trẻ trung, &ocirc;m s&aacute;t cơ thể nam giới.</p>\n"
                + "<p style=\"text-align: justify;\">Chi tiết tại <strong>LINK</strong>:</p>\n"
                + "<p style=\"text-align: justify;\"><a href=\"http://ho.lazada.vn/SHUrKd\" target=\"_blank\" rel=\"noopener\" data-ft=\"{&quot;tn&quot;:&quot;-U&quot;}\" data-lynx-mode=\"async\" data-lynx-uri=\"https://l.facebook.com/l.php?u=http%3A%2F%2Fho.lazada.vn%2FSHUrKd&amp;h=ATPYJY6DXiO5YkNIfEi13tYuP0_Bv_jOyeADCrYdGVP38q_z9k0R7M4xOmFd9UeigxH5vW9WBCxj0VKPxWmYwpkCjO6yEIxX11GxkS7wEcOqw7Q_8rHNk7ui_rwQL1bI7E7YjOILSEiQYTDs7i_4fybmjmpVdSMuBY2Tp-siZTf6gd61DDQG9bMy9dhKa1cng2XfkRiRiO2m5YX0Xm5SK5NZbpcd-E1MBOpZrLx_WG5XXKG4ZY4Ol0YJkUgNaOWQyiVu2z1o9ag_tDT0B2Lss3al7nvbCYcp0jhaJ0rmdAU\">http://ho.lazada.vn/SHUrKd</a></p>\n"
                + "<p style=\"text-align: justify;\">Facebook: <a href=\"https://www.facebook.com/khuyenmaicucchat.19350811/\">Khuyến m&atilde;i cực chất</a></p>\n"
                + "<p style=\"text-align: justify;\">Tr&acirc;n trọng cảm ơn!</p>\n"
                + "<p style=\"text-align: justify;\">&nbsp;</p>";

        String numMail = Config.NUMBER_MAIL;
        String status = Config.STATUS_MAIL_SEND;
        String statusUpdate = Config.STATUS_MAIL_UPDATE;
        ArrayList<Mail> lst = getListMail(status, numMail);
        //test mail thi set nhu nay
//        ArrayList<Mail> lst = new ArrayList<>();
//        Mail m = new Mail();
//        m.setEmail("tunglv9x@gmail.com");
//        lst.add(m);
        for (Mail to : lst) {
            try {
                sendEmail(from, pwd, to.getEmail(), title, content);
                System.out.println("tunglv gui toi mail" + to.getEmail() + " thanh cong");
                //update status
                to.setStatus(Integer.parseInt(statusUpdate));
                MailDao.updateMail(to);
            } catch (Exception e) {
                System.out.println("tunglv gui toi mail: " + to.getEmail() + " bi loi" + e.getMessage());
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
            //msg.setText(content);
            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = content + "<img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:\\Users\\PMDVCNTT\\Documents\\GitHub\\CustomerInsight\\src\\images\\img.PNG");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            msg.setContent(multipart);
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
    public static void main(String[] args) throws SQLException, Exception {
        sendMultiEmail();
    }
}
