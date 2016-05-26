package com.lovejjfg.blogdemo.model.bean;

/**
 * Created by Joe on 2016-05-25
 * Email: lovejjfg@gmail.com
 */
public class BlogBean {
    String tittle;
    String url;
    String time;
    String times;

    public BlogBean(String tittle, String url, String time, String times) {
        this.tittle = tittle;
        this.url = url;
        this.time = time;
        this.times = times;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

}
