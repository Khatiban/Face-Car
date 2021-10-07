package com.fury.facecar.Model;

/**
 * Created by FURY on 2/22/2018.
 */

public class Video_List {

    String id;
    String url;
    String title;
    String like;
    String view;
    String urljson;

    public Video_List(String id, String url, String title, String like, String view, String urljson) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.like = like;
        this.view = view;
        this.urljson = urljson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String geturljson() {
        return urljson;
    }

    public void seturljson(String view) {
        this.urljson = view;
    }
}
