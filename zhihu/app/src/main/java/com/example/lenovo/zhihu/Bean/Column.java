package com.example.lenovo.zhihu.Bean;

/**
 * Created by lenovo on 2018/12/14.
 */

public class Column {
private String name;
private String thumbnail;
private String  description;
private  int id;
   public String getName(){return name;}

    public int getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}

