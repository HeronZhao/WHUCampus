package com.example.hp.mycampus.model;

public class News {
    // 名字，介绍，图片
    private String name;
    private String des;
    private String photoId;

    // 构造函数
    public News(String name, String des, String picId) {
        this.des = des;
        this.name = name;
        this.photoId = picId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getDes() {

        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
