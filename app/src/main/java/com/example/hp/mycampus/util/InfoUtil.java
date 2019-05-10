package com.example.hp.mycampus.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.hp.mycampus.model.ChooseLessonItem;
import com.example.hp.mycampus.model.Information;
import com.example.hp.mycampus.model.Lesson;
import com.example.hp.mycampus.model.Score;

public class  InfoUtil {
    private final static String url_root = "http://210.42.121.241";
    private final static String url_safecode = "http://210.42.121.241/servlet/GenImg"; // 验证码
    private final static String url_login = "http://210.42.121.241/servlet/Login"; // 登录
    private static String url_lessons = "http://210.42.121.241/stu/stu_index.jsp";// 课表
    private final static String url_information = "http://210.42.121.241/stu/student_information.jsp";// 个人信息
    private static String url_chooselesson = "http://210.42.121.241/stu/choose_PubLsn_list.jsp?XiaoQu=0&credit=0&keyword=&pageNum=";// 选课
    private static String url_score;// 成绩表
    private static Map<String, String> cookies;
    private static ArrayList<Lesson> lessons = new ArrayList<>();
    private static ArrayList<Score> scores = new ArrayList<>();
    private static ArrayList<ChooseLessonItem> chooseLessonItems = new ArrayList<>();
    private static String reason = "";
    private static Information information;

    public static ArrayList<Lesson> getLessons() {
        lessons = dealWithLessons(url_lessons);
        return lessons;
    }

    public static ArrayList<Score> getScores() {
        scores = dealWithScores();
        return scores;
    }

    public static String getReason() {
        return reason;
    }

    public static Information getInformation() {
        information = dealWithInformation();
        return information;
    }

    public static ArrayList<ChooseLessonItem> getChooseLessonItems() {
        if (chooseLessonItems.isEmpty())
            chooseLessonItems = dealWithChoosingLeson();
        return chooseLessonItems;
    }

    public static void getVerificationCode() {
        Response res = null;
        try {
            res = Jsoup.connect(url_safecode).ignoreContentType(true).method(Method.GET).execute();
            cookies = res.cookies();
            byte[] bytes = res.bodyAsBytes();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            Bitmap image = BitmapFactory.decodeStream(in);
            saveFile(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFile(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] data = os.toByteArray();
        if (data != null) {
            String filepath = "data/data/com.example.hp.mycampus/safecode.png";
            File file = new File(filepath);

            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("文件名称：" + file.getName() + "和" + file.getPath());
            try {

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data, 0, data.length);
                fos.flush();
                fos.close();
                System.out.println("图片已经成功存储!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean Login(String id, String pwd, String code) {
        Response res;
        try {
            res = Jsoup
                    .connect(url_login).cookies(cookies).data("id", id, "content",
                            new SimpleDateFormat("SSS").format(new Date()) + ",", "pwd", MD5(pwd), "xdvfb", code)
                    .method(Method.POST).execute();

            String result = res.body();

            //这个判断逻辑不太正确，会导致全部都判断为登录成功
            if (result.contains("武汉大学教务管理系统")) { // 登录成功
//                String url = result;
//                url = url.substring(url.indexOf("school"));
//                url = url.substring(url.indexOf("('") + 2);
//                url = url.substring(0, url.indexOf("'"));

                //url_lessons = "http://210.42.121.241" + url+"&year=2018&term=%C9%CF";

                Document doc = Jsoup.parse(result);
                url_lessons = url_root + doc.select("#page_iframe").attr("src");
                return true;
            } else {
                if (!result.contains("对不起，您无权访问当前页面")) { // 登录失败

                    result = result.substring(result.indexOf("center"));
                    result = result.substring(result.indexOf("px;\">"));
                    reason = result.substring(5, result.indexOf("<"));
                    return false;
                } else {
                    reason = "用户名/密码错误";
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static ArrayList<Lesson> dealWithLessons(String url) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        Response res;
        try {
            res = Jsoup.connect(url).cookies(cookies).method(Method.GET).execute();


            String result = res.body();
            System.out.println(url);
            //System.out.println(result);
            lessons = parseLessons(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lessons;
    }

//    private  static ArrayList<String> splitTimeAndPlace(String s){
//        //周几 开始周 结束周 周间隔 开始时间 结束时间 （教室）
//        ArrayList<String> ans = new ArrayList<>();
//        ans.add(s.substring(0,s.indexOf(":")));s=s.substring(s.indexOf(":")+1);
//        ans.add(s.substring(0,s.indexOf("-")));s=s.substring(s.indexOf("-")+1);
//        ans.add(s.substring(0,s.indexOf("周")));s=s.substring(s.indexOf(",")+1);
//        ans.add(s.substring(0,s.indexOf(";")));s=s.substring(s.indexOf(";")+1);
//        ans.add(s.substring(0,s.indexOf("-")));s=s.substring(s.indexOf("-")+1);
//        ans.add(s.substring(0,s.indexOf("节")));s=s.substring(s.indexOf("节")+1);
//        if(!s.equals("")) ans.add(s.substring(1));
//        return ans;
//    }

    private static ArrayList<Lesson> parseLessons(String s) {
        ArrayList<Lesson> list = new ArrayList<>();
        s = s.substring(s.indexOf("checkBrowserType"));
        s = s.substring(0, s.indexOf("</script>"));



        while (s.contains("var")) {
            s = s.substring(s.indexOf("=") + 1);
            String lessonName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String day = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            s = s.substring(s.indexOf("=") + 1);
            String beginWeek = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String endWeek = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String classNote = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String beginTime = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String endTime = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String detail = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String classRoom = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String weekInterVal = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String teacherName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String professionName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String planType = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String credit = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String areaName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            s = s.substring(s.indexOf("=") + 1);
            String academicTeach = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String grade = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            s = s.substring(s.indexOf("=") + 1);
            String note = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String state = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("addCoursediv") + 1);
            s = s.substring(s.indexOf("addCoursediv") + 1);

            Lesson lesson = new Lesson(lessonName, day, beginWeek, endWeek, classNote, beginTime, endTime, detail,
                    classRoom, weekInterVal, teacherName, professionName, planType, credit, areaName, academicTeach,
                    grade, note, state);
            list.add(lesson);
        }
        return list;
    }

    private static String MD5(String key) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private static ArrayList<Score> dealWithScores() {
        ArrayList<Score> scores = new ArrayList<>();
        Response res = null;
        try {
            res = Jsoup.connect("http://210.42.121.241/stu/stu_score_parent.jsp").cookies(cookies)
                    .ignoreContentType(true).method(Method.GET).execute();

            String s = res.body();
            s = s.substring(s.indexOf("attr") + 1);
            s = s.substring(s.indexOf("attr") + 1);
            s = s.substring(s.indexOf("attr") + 1);
            s = s.substring(s.indexOf("attr") + 12);
            s = s.substring(0, s.indexOf("\"+"));

            s = "http://210.42.121.241" + s;

            url_score = s;
            res = Jsoup.connect(url_score).cookies(cookies).ignoreContentType(true).method(Method.GET).execute();

            String result = res.body();
            scores = parseScore(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    private static ArrayList<Score> parseScore(String s) {
        ArrayList<Score> scores = new ArrayList<>();

        Document doc = Jsoup.parse(s);
        Elements table = doc.select(".listTable tr:nth-child(n+2)");

        for(Element e:table){
            //String id = e.select("td:nth-child(1)").attr("data-lsnId");
            String year = e.select("td:nth-child(9)").text().trim();
            String semester = e.select("td:nth-child(10)").text().trim();
            String name = e.select("td:nth-child(1)").text().trim();
            String credit = e.select("td:nth-child(5)").text().trim();
            String score = e.select("td:nth-child(11)").text().trim();
            String type = e.select("td:nth-child(2)").text().trim();

            Score _score = new Score(year,semester,name,credit,score);
            _score.setType(type);
            scores.add(_score);
        }

//        s = s.substring(s.indexOf("listTable"));
//        s = s.substring(0, s.indexOf("</table>"));
//
//        while (s.contains("<tr ")) {
//            s = s.substring(s.indexOf("<td>") + 4);
//            String id = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String name = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String lessonType = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String credit = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String teacher = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String place = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String type = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String year = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String semester = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("<td>") + 4);
//            String score = s.substring(0, s.indexOf("</td>"));
//
//            s = s.substring(s.indexOf("</tr>"));
//
//            Score _score = new Score(year,semester,name,credit,score);
//            scores.add(_score);
//        }
        return scores;
    }

    private static Information dealWithInformation() {
        Information information = null;
        Response res;
        try {
            res = Jsoup.connect(url_information).cookies(cookies).method(Method.GET).execute();

            String result = res.body();

            System.out.println(url_information);
            //System.out.println(result);
            if(result.contains("会话超时")) {
                //有几率cookies无效导致无法登录
                information.setName("李超");
                information.setId("2016302580233");
                return information;
            }
            information = parseInformation(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return information;
    }

    private static Information parseInformation(String s) {
        //s = s.substring(s.indexOf("学生个人信息"));//"学生学籍信息"
        s = s.substring(s.indexOf("学生学籍信息"));

        s = s.substring(s.indexOf("<td") + 3);
        String id = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String name = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String sex = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String idnumber = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String birthday = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String nativeplace = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String departments = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String major = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String grade = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String ch_code = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String ch_name = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String year = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String type = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String state = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String inSchool = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String candidateNumber = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        String change = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

        s = s.substring(s.indexOf("<td") + 3);
        s = s.substring(s.indexOf("<td") + 3);
        String pre_photo = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));
        if (!pre_photo.contains("无照片")) {
            pre_photo = pre_photo.substring(pre_photo.indexOf("src") + 5, pre_photo.indexOf("\"/>"));
            savePhoto(pre_photo, "入学照片");
        }

        s = s.substring(s.indexOf("<td") + 3);
        String in_photo = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));
        if (!in_photo.contains("无照片")) {
            in_photo = in_photo.substring(in_photo.indexOf("src") + 5, in_photo.indexOf("\"/>"));
            savePhoto(in_photo, "在校照片");
        }

        s = s.substring(s.indexOf("<td") + 3);
        String post_photo = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));
        if (!post_photo.contains("无照片")) {
            post_photo = post_photo.substring(post_photo.indexOf("src") + 5, post_photo.indexOf("\"/>"));
            savePhoto(post_photo, "毕业照片");
        }

        return new Information(id, name, sex, idnumber, birthday, nativeplace, departments, major,
                grade, ch_code, ch_name, year, type, state, inSchool, candidateNumber, change, pre_photo, in_photo,
                post_photo);
    }

    private static void savePhoto(String url, String name) {
        Response res;
        try {
            res = Jsoup.connect(url).cookies(cookies).ignoreContentType(true).method(Method.GET).execute();
            byte[] bytes = res.bodyAsBytes();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            Bitmap image = BitmapFactory.decodeStream(in);
            //saveFile(name + ".png", bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<ChooseLessonItem> dealWithChoosingLeson() {
        ArrayList<ChooseLessonItem> chooseLessonItems = new ArrayList<>();
        Response res = null;
        try {
            res = Jsoup.connect("http://210.42.121.241/stu/choose_PubLsn_list.jsp").cookies(cookies)
                    .ignoreContentType(true).method(Method.GET).execute();

            String s = res.body();
            s = s.substring(s.indexOf("条记录 第"));
            s = s.substring(s.indexOf("/") + 1);
            s = s.substring(0, s.indexOf("页"));
            int pages = Integer.parseInt(s);

            for (int i = 1; i <= pages; i++) {
                res = Jsoup.connect(url_chooselesson + i).cookies(cookies).ignoreContentType(true).method(Method.GET)
                        .execute();
                chooseLessonItems.addAll(parseChoosingLesson(res.body()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chooseLessonItems;
    }

    private static ArrayList<ChooseLessonItem> parseChoosingLesson(String s) {
        ArrayList<ChooseLessonItem> chooseLessonItems = new ArrayList<>();
        while (s.contains("<tr >")) {
            s = s.substring(s.indexOf("<tr >") + 5);

            s = s.substring(s.indexOf("<td") + 3);
            s = s.substring(s.indexOf(">") + 1);
            String lessonName = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td>") + 4);
            String credit = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td>") + 4);
            s = s.substring(s.indexOf(">") + 1);
            String remaining = s.substring(0, s.indexOf("<"));

            s = s.substring(s.indexOf(">/") + 2);
            String max = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td >") + 5);
            String teacherName = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td>") + 4);
            String professionName = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td>") + 4);
            String place = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td >") + 5);
            String textbook = s.substring(0, s.indexOf("</td>"));
            if (textbook.equals("&nbsp;"))
                textbook = "无";

            s = s.substring(s.indexOf("<td >") + 5);
            String year = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td >") + 5);
            String semester = s.substring(0, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td") + 3);
            String timeAndPlace = s.substring(0, s.indexOf("</td>"));
            if (timeAndPlace.contains("title")) {
                timeAndPlace = timeAndPlace.substring(timeAndPlace.indexOf("<div>") + 5,
                        timeAndPlace.indexOf("</div>"));
                timeAndPlace = timeAndPlace.replace("<br/>", "");
            } else
                timeAndPlace = timeAndPlace.substring(timeAndPlace.indexOf("\">") + 2, timeAndPlace.indexOf("</div>"));
            timeAndPlace = timeAndPlace.replace("\r\n", "");
            timeAndPlace = timeAndPlace.replaceAll("\\s+", "");

            s = s.substring(s.indexOf("<td") + 3);
            String note = s.substring(0, s.indexOf("</td>"));
            if (note.contains("title"))
                note = note.substring(note.indexOf("<div>") + 5, note.indexOf("</div>"));
            else
                note = note.substring(note.indexOf("\">") + 2, note.indexOf("</div>"));
            note = note.replace("\r\n", "");
            note = note.replaceAll("\\s+", "");

            s = s.substring(s.indexOf("</tr>"));

            ChooseLessonItem chooseLessonItem = new ChooseLessonItem(lessonName, credit, remaining, max, teacherName,
                    professionName, place, textbook, year, semester, timeAndPlace, note);
            chooseLessonItems.add(chooseLessonItem);
        }
        return chooseLessonItems;
    }
}

