package com.example.hp.mycampus.util;

import com.example.hp.mycampus.model.Book;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

public class LibraryInfo {
    private static ArrayList<Book> currentList = new ArrayList<>();
    private static ArrayList<Book> historyList = new ArrayList<>();

    public static ArrayList<Book> getCurrentList() {
        return currentList;
    }

    public static ArrayList<Book> getHistoryList() {
        return historyList;
    }

/*    public static void main(String[] args) {
        Login("2016302580332", "260050");
        // Login("2016302580121", "ZYF760286098");
        System.out.println("123");
        for (Book book : historyList)
            System.out.println(book);
    }*/


    public static boolean Login(String id, String pwd) {
        Response res = null;
        try {
            res = Jsoup.connect("http://metalib.lib.whu.edu.cn/pds").data("func", "login", "institute", "WHU", "url",
                    "http://opac.lib.whu.edu.cn/F/HBRDTCNREP4IB492KJ2MYNSPMU1GYIHXM1AS93XCQQL365VKPR-14397?func=bor-info&",
                    "bor_id", id, "bor_verification", pwd).method(Method.POST).execute();

            String url1 = res.body();
            if (url1.contains("relocate")) {
                url1 = url1.substring(url1.indexOf("noscript"));
                url1 = url1.substring(url1.indexOf("logon/") + 6);
                url1 = url1.substring(0, url1.indexOf("\">"));

                res = Jsoup.connect(url1).method(Method.GET).execute();
                String url2 = res.body();
                url2 = url2.substring(url2.indexOf("url") + 7);
                url2 = url2.substring(1, url2.indexOf("';"));
                url2 = url2.substring(url2.indexOf("http"));

                String s1 = url2.substring(0, url2.indexOf("=") + 1);
                String s2 = url2.substring(url2.indexOf("+&"), url2.indexOf("&calling_system"));

                res = Jsoup.connect(s1 + "bor-loan" + s2).method(Method.GET).execute();
                currentList = parseCurrent(res.body());

                res = Jsoup.connect(s1 + "bor-history-loan" + s2).method(Method.GET).execute();
                historyList = parseHistory(res.body());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static ArrayList<Book> parseCurrent(String s) {
        ArrayList<Book> list = new ArrayList<>();

        if (s.contains("您当前没有任何在借单册"))
            return list;

        return list;
    }

    private static ArrayList<Book> parseHistory(String s) {
        ArrayList<Book> list = new ArrayList<>();

        if (s.contains("借阅历史列表中您没有任何单册"))
            return list;

        s = s.substring(s.indexOf("<table border=0 cellspacing=2>"));
        s = s.substring(0, s.indexOf("</table>"));

        while (s.contains("<tr>")) {
            s = s.substring(s.indexOf("<tr>") + 4);
            s = s.substring(s.indexOf("HREF") + 5);
            String url = s.substring(0, s.indexOf(">"));
            String[] moreInfo = getMoreInfo(url);

            s = s.substring(s.indexOf("<td") + 3);
            String author = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td") + 3);
            String title = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td") + 3);
            String year = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td") + 3);
            String shouldReturnDate = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));
            shouldReturnDate = shouldReturnDate.replace("<br>", "");

            s = s.substring(s.indexOf("<td") + 3);
            String shouldReturnTime = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td") + 3);
            String returnDate = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td") + 3);
            String returnTime = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

            s = s.substring(s.indexOf("<td") + 3);
            String fine = s.substring(s.indexOf(">") + 1, s.indexOf("<"));
            fine = fine.replaceAll("\\s+", "");

            s = s.substring(s.indexOf("<td") + 3);
            String branch = s.substring(s.indexOf(">") + 1, s.indexOf("</td>"));

            //Book book = new Book(author, title, year, shouldReturnDate, shouldReturnTime, returnDate, returnTime, fine, branch, moreInfo[0], moreInfo[1], moreInfo[2]);
            //Book book = new Book(title);
            Book  book = new Book(author,title,year,shouldReturnDate,shouldReturnTime,returnDate,returnTime,branch,fine);
            list.add(book);
        }
        return list;
    }

    private static String[] getMoreInfo(String url) {
        String[] result = new String[3];
        Response res = null;
        try {
            res = Jsoup.connect(url).method(Method.GET).execute();
            String s = res.body();

            s = s.substring(s.indexOf("外借日期"));
            s = s.substring(s.indexOf("<td") + 3);
            result[0] = s.substring(s.indexOf(">") + 1, s.indexOf("<"));

            s = s.substring(s.indexOf("最后续借日期"));
            s = s.substring(s.indexOf("<td") + 3);
            result[1] = s.substring(s.indexOf(">") + 1, s.indexOf("<"));

            s = s.substring(s.indexOf("续借次数"));
            s = s.substring(s.indexOf("<td") + 3);
            result[2] = s.substring(s.indexOf(">") + 1, s.indexOf("<"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
