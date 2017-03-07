package com.lovejjfg.family;

import com.lovejjfg.blogdemo.utils.BaseUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23, manifest = Config.NONE)
public class ApiTest extends BaseTest {


    //    private LoginApi loginApi;
    @Override
    @Before
    public void setUp() {
        super.setUp();
//        activity = Robolectric.buildActivity(MainActivity.class).create().get();
//        userService = BaseDataManager.getUserService();
//        loginApi = BaseDataManager.getLoginApi();
    }

    @After
    public void tearDown() {

    }


    @Test
    public void testVersion() throws Exception {
        int versionCode = BaseUtil.getVersionCode(context);
        String versionName = BaseUtil.getVersionName(context);
        System.out.println(versionCode);
        System.out.println(versionName);

    }

    @Test
    public void testJsoup() throws IOException {
        Document document = Jsoup.connect("https://android-arsenal.com/user/lovejjfg").get();
        Elements select = document.select("div.container.content");
        for (Element element : select) {
            String tittle = element.select("a[href]").first().text();
            System.out.println("name: " + tittle);
            String url = element.select("a").first().attr("href");
            System.out.println("url: " + url);
            String attr = element.select("img").attr("src");
            System.out.println("头像: " + attr);

            String email = element.select("a.email").text();
            System.out.println("email: " + email);
            String urls = element.select("a.url").text();
            System.out.println("urls: " + urls);

            String location = element.select("dt:contains(Location) + dd").first().text();
            System.out.println("call: " + location);
            element.select("div.moduletable_events > ul");
            Elements h2Tags = element.select("h2");
            for (Element e : h2Tags) {
                System.out.println("call: " + e.toString());
//                            Elements ul = e.select("div.moduletable_events > ul");
                Elements li = e.select("li");
                for (Element l : li) {
                    System.out.println("call: " + l.toString());
                }

            }
        }
    }

    @Test
    public void testJsoup2() throws IOException {
        Document document = Jsoup.connect("https://android-arsenal.com/user/lovejjfg").get();
//        Elements select = document.select("div.container.content");
        Elements select = document.select("div.container.content")
                .select("ul> li");

        for (Element element : select) {
            System.out.println(element.toString());
        }
    }


}
