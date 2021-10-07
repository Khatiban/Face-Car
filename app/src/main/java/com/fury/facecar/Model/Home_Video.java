package com.fury.facecar.Model;

/**
 * Created by FURY on 2/2/2018.
 */

public class Home_Video {

    String sub;
    String url_image;
    String id_post;
    String UrlJson;

    public Home_Video(String sub, String url_image, String id_post, String UrlJson) {
        this.sub = sub;
        this.url_image = url_image;
        this.id_post = id_post;
        this.UrlJson = UrlJson;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getUrlJson() {
        return UrlJson;
    }

    public void setUrlJson(String id_post) {
        this.UrlJson = id_post;
    }
}
