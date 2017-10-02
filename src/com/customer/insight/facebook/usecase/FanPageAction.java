/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.facebook.usecase;

import com.customer.insight.config.Config;
import com.customer.insight.db.MailDao;
import com.customer.insight.entity.Mail;
import com.customer.insight.facebook.dto.Comment;
import com.customer.insight.facebook.dto.Feed;
import com.customer.insight.facebook.dto.Page;
import com.customer.insight.facebook.dto.User;
import com.customer.insight.http.ResponseUtil;
import com.customer.insight.mail.EmailAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * desc: Lay id cua trang ca nhan
 *
 * @author TUNGLV
 */
//dau vao ten username cua trang
//vao trang do ta co thong tin username nhu sau @Torano.vn -> username='Torano.vn'
public class FanPageAction {

    public static void main(String[] args) throws Exception {
        FanPageAction fanPage = new FanPageAction();
        Config cfg = new Config();
        String token = cfg.USER_ACCESS_TOKEN;
        //tai khoan nguoi dung tren trang
        String username = "mshoatoeic";
        //lay thong tin trang
        Page page = fanPage.getPageInfo(token, username);
        //lay danh sach bai da dang tu ngay truyen vao den hien tai
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2017-09-28");
        ArrayList<Feed> lstFeed = fanPage.getFeed(token, page.getId(), fromDate);
        //lay danh sach binh luan theo bai dang
        String mail = null;
        for (Feed f : lstFeed) {
            ArrayList<Comment> comments = fanPage.getComments(token, f.getId());
            for (Comment c : comments) {
                //neu co email thi gui mail
                String comment = c.getContentComment();
                if (comment.contains("@")) {
                    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(comment);
                    while (m.find()) {
                        try {
                            mail = m.group();
                            char end = mail.charAt(mail.length() - 1);
                            if (end == '.') {
                                mail = mail.substring(0, mail.length() - 1);
                            }
                            String from = "lazada.ohaythe@gmail.com";
                            String pwd = "123456a@";
                            String title = "Chuột quang không dây Forter V189 V111 + Tặng miếng lót chuột  ";
                            String content = "CLICK ĐỂ XEM CHI TIẾT VÀ MUA HÀNG: http://bit.ly/2hyREyh " + "\n"
                                    + "Là sản phẩm cách tân vừa ra mắt năm 2014 hỗ trợ một cách hoàn hảo công việc văn phòng của bạn, chuột không dây Forter V189 có thiết kể trẻ trung, trang nhã và độ phân giải cao đến 1500dpi. Điểm nổi bật nhất của sản phẩm là khả năng tự động tắt, bật khi kết nối hoặc shutdown máy tính. Đây sẽ là người bạn hỗ trợ đắc lực cho mọi công việc trên máy tính của bạn. Tiếp nối những tính năng nổi trội của các sản phẩm trước, Forter V189 được cải tiến và nâng cấp đáp ứng nhu cầu làm việc với cường độ và áp lực cao.\n"
                                    + "\n"
                                    + "TÍNH NĂNG NỔI BẬT\n"
                                    + "Thiết kế Ergonomic, 3 phím chức năng\n"
                                    + "Nhờ vào thiết kế Ergonomic thông minh, chuột không dây Forter V189 tạo sự thoải mái cho cả người thuận tay phải lẫn tay trái. Sản phẩm được trang bị 3 phím chức năng cơ bản giúp người dùng dễ dàng thao tác, có cảm giác chắc chắn khi di chuyển chuột với cường độ nhanh.\n"
                                    + "\n"
                                    + "Độ phân giải 1500dpi, khoảng cách kết nối 15m\n"
                                    + "Sản phẩm có độ phân giải cao lên đến 1500dpi giúp bạn thao tác nhanh, nhạy trong phạm vi 15m. Vậy nên, dù phải di chuyển liên tục trong văn phòng, bạn vẫn có thể thực hiện công việc trên máy tính một cách nhanh chóng, chính xác.\n"
                                    + "\n"
                                    + "Tự động bật, tắt & tiết kiệm năng lượng\n"
                                    + "Với công nghệ tiên tiến, Forter V189 có khả năng tự động bật, tắt khi máy tính khởi động hoặc shutdown. Điều này giúp tiết kiệm năng lượng tối đa, cao hơn gấp 3 lần so với sản phẩm thông thường trên thị trường. Ngoài ra, sản phẩm được trang bị Pin 1AA Ultra Alkaline có tuổi thọ lên đến 24 tháng.\n"
                                    + "\n"
                                    + "THÔNG TIN SẢN PHẨM\n"
                                    + "Màu: đen\n"
                                    + "Trọng lượng (KG): 0.1\n"
                                    + "Kích thước sản phẩm (D x R x C cm): 10x3x2\n"
                                    + "Bảo hành: 24 tháng- Theo đúng tiêu chuẩn của Nhà sản xuất";
                            //gui mail
                            //EmailAction.sendEmail(from, pwd, mail, title, content);
                            Mail email = new Mail();
                            email.setEmail(mail);
                            //select  count (*), email  from TBL_MAIL   group by email having count(*)>1 ;
                            if (!EmailAction.checkMailExisted(mail)) {
                                MailDao.insert(email);
                            }

                        } catch (Exception e) {

                        }
                    }
                }

            }
        }
        System.out.println("thuc hien thanh cong");
    }

    //lay thong tin trang theo username
    public Page getPageInfo(String token, String username) throws Exception {
        Page page = new Page();
        JSONParser parser = null;
        String urlGetPage = "https://graph.facebook.com/" + username + "?access_token=" + token;
        String jsonStr = ResponseUtil.sendGet(urlGetPage);
        parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonStr);
        page.setId(obj.get("id").toString());
        page.setName(obj.get("name").toString());
        return page;
    }

    //lay thong tin cac bai da dang tren trang idPage tu ngay fromDate
    //truyen vao fromDate, 
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //Date from = sdf.parse("2017-09-28");
    public ArrayList<Feed> getFeed(String token, String idPage, Date fromDate) throws Exception {
        JSONParser parser = null;
        String urlPageFeed = "https://graph.facebook.com/" + idPage + "/feed?access_token=" + token;
        String rs = ResponseUtil.sendGet(urlPageFeed);
        parser = new JSONParser();
        JSONObject objFeed = (JSONObject) parser.parse(rs);
        JSONArray data = (JSONArray) objFeed.get("data");
        ArrayList<Feed> lstFeed = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject feed = (JSONObject) data.get(i);
            String creat_time = feed.get("created_time").toString();
            //2017-09-27T08:29:04+0000
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date dateCreate = sdf.parse(creat_time);

            if (dateCreate.after(fromDate) || dateCreate.equals(fromDate)) {
                Feed f = new Feed();
                Object keyMessage = feed.get("message");
                Object keyStory = feed.get("story");
                f.setId(feed.get("id").toString());
                f.setCreateTime(creat_time);
                if (keyMessage != null) {
                    f.setMessage(feed.get("message").toString());
                }
                if (keyStory != null) {
                    f.setStory(feed.get("story").toString());
                }
                lstFeed.add(f);
            }

        }
        return lstFeed;
    }

    //lay danh sach binh luan cua bai dang
    //binh luan thuoc bai viet nao 
    //ai binh luan
    //noi dung binh luan la gi
    //lstFeed: danh sach bai dang cua trang
    public ArrayList<Comment> getComments(String token, String feedId) throws Exception {
        ArrayList<Comment> listComment = new ArrayList<>();
        JSONParser parser = null;
        String urlGetComment = "https://graph.facebook.com/v2.10/" + feedId + "/comments?access_token=" + token;
        String rsComment = ResponseUtil.sendGet(urlGetComment);
        parser = new JSONParser();
        JSONObject objComments = (JSONObject) parser.parse(rsComment);
        JSONArray dataComments = (JSONArray) objComments.get("data");
        List<Comment> lst = new ArrayList<Comment>();
        if (dataComments != null && dataComments.size() > 0) {
            for (int j = 0; j < dataComments.size(); j++) {
                Comment c = new Comment();
                //chu y dataComments get j khong duoc nham bien
                JSONObject comment = (JSONObject) dataComments.get(j);
                c.setId(comment.get("id").toString());
                c.setIdFeed(feedId);
                Object user = comment.get("from");
                if (user != null) {
                    String userJson = comment.get("from").toString();
                    parser = new JSONParser();
                    JSONObject objUser = (JSONObject) parser.parse(userJson);
                    User u = new User();
                    u.setId(objUser.get("id").toString());
                    u.setName(objUser.get("name").toString());
                    c.setUser(u);
                }
                c.setTimeComment(comment.get("created_time").toString());
                c.setContentComment(comment.get("message").toString());
                //trong lst chua danh sach nguoi binh luan userName
                listComment.add(c);
            }
        }
        return listComment;
    }
}
