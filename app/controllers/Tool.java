package controllers;

import models.Bookmarker;
import models.BookmarkerViewer;
import models.Config;
import myutils.ProxyUrl;
import myutils.ResultBuilder;
import play.modules.gae.GAE;
import play.mvc.Before;
import play.mvc.Controller;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 工具.
 */
public class Tool extends Controller {
    @Before
    static void checkConnected() {
        if (GAE.getUser() == null) {
            Application.login();
        } else {
            renderArgs.put("user", GAE.getUser().getEmail());
        }
    }

    /**
     * 工具主页.
     */
    public static void index() {
        renderArgs.put("homepage", Config.getHomepage(getUser()));

        render();
    }

    /**
     * 初始化站点数据.
     */
    public static void init_mysite_datas() {
        BookmarkerViewer currUserBookmarkers = Bookmarker.viewer(getUser());

        //清空书签
        currUserBookmarkers.deleteAll();
        //添加默认书签列表
        currUserBookmarkers.add("javaeye", "JavaEye", "http://www.javaeye.com")
                .add("infoq", "infoq", "http://www.infoq.com")
                .add("infoqcn", "info中国", "http://www.infoq.com/cn")
                .add("github", "github", "http://github.com")
                .add("pragrammingscala", "pragrammingscala", "http://programming-scala.labs.oreilly.com")
                .add("tianya", "天涯房产", "http://www.tianya.cn/publicforum/articleslist/0/house.shtml")
                .add("tieba", "湖南贴吧", "http://tieba.baidu.com/f?kw=%BA%FE%C4%CF%CE%C0%CA%D3")
                .add("sina", "新浪", "http://news.sina.com.cn");

       renderJSON(ResultBuilder.get().msg("设置成功!").toJson());
    }

    /**
     * 我的搜索.
     */
    public static void search() {
        String key = params.get("key");
        renderArgs.put("key", key);

        //倒墙?
        if (key != null && key.startsWith("http")) {
            proxy(key);
            return;
        }

        renderArgs.put("googleurl", "http://www.google.cn/search?q=" + key);
        renderArgs.put("bingurl", "http://www.bing.com/search?q=" + key);
        render();
    }

    /**
     * 翻墙.
     */
    public static void proxy(final String url) {
        HttpURLConnection conn = null;
        InputStream is = null;
        StringWriter writer = new StringWriter();
        final String targetURL = ProxyUrl.real(url);
        System.out.println("target url:" + targetURL);

        try {
            conn = (HttpURLConnection) new URL(targetURL).openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setInstanceFollowRedirects(true);
            conn.setUseCaches(false);

            is = conn.getInputStream();
            byte[] buff = new byte[1024];
            int size = 0;

            while ((size = is.read(buff)) != -1) {
                writer.write(new String(buff, 0, size));
            }
        } catch (Exception e) {
            e.printStackTrace();
            writer.write("发生错误!" + e.getMessage());

        } finally {
            if (is != null) try {
                is.close();
            } catch (Exception e) {
            }

            writer.flush();

            final String content = writer.toString().replaceAll("href", "value").replaceAll("src", "value");

            renderArgs.put("content", content);
            render();
        }

    }

    /**
     * 添加书签.
     */
    public static void add_bookmarker() {
        String key = params.get("key");
        String name = params.get("name");
        String url = params.get("url");

        Bookmarker.add(key, name, url, getUser());

        renderJSON(ResultBuilder.get().msg("操作成功!").toJson());
    }

    static String getUser() {
        return renderArgs.get("user", String.class);
    }

    public static void set_homepage(String homepage) {
        Config.setHomepage(homepage, getUser());

        renderJSON(ResultBuilder.get().msg("操作成功!").toJson());
    }
}
