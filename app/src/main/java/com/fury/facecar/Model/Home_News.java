package com.fury.facecar.Model;

/**
 * Created by FURY on 2/2/2018.
 */

public class Home_News {

    String sub;
    String url_image;
    String id_post;
    String tit;
    String view;
    String like;

    public Home_News(String sub, String url_image, String id_post, String tit, String view, String like) {
        this.sub = sub;
        this.url_image = url_image;
        this.id_post = id_post;
        this.tit = tit;
        this.view = view;
        this.like = like;
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

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
