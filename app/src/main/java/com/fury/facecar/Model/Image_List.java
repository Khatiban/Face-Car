package com.fury.facecar.Model;

/**
 * Created by FURY on 4/2/2018.
 */

public class Image_List {

    String id;
    String url;
    String urlImage;
    String view;
    String like;
    String sub;


    public Image_List(String id, String url, String urlImage,String view, String like, String sub) {
        this.id = id;
        this.url = url;
        this.urlImage = urlImage;
        this.view = view;
        this.like = like;
        this.sub = sub;
    }


    public void setView(String view) {

        this.view = view;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getView() {

        return view;
    }

    public String getLike() {
        return like;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String urlImage) {
        this.sub = urlImage;
    }
}
