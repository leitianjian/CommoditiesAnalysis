package com.group.commditiesAnalysis.model;

import com.google.gson.Gson;
import com.group.commditiesAnalysis.Utils.infoProcess.DateProcess;

import java.util.ArrayList;
import java.util.Date;

public class ItemBean {
    private String id;
    private WebType type;
    private String url;
    private ArrayList<RateBean> comments;

    public ItemBean (String url, String id, WebType type, ArrayList<RateBean> comments) {
        this.url = url;
        this.id = id;
        this.type = type;
        this.comments = comments;
    }

    public ArrayList<RateBean> getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }

    public static void main(String[] args) {
        ArrayList<RateBean> comments = new ArrayList<>();
        comments.add(new RateBean("hhhh", "hhhhh", DateProcess.getDate("1971-05-31", "yyyy-MM-dd")));
        comments.add(new RateBean("llll", "lllll", DateProcess.getDate("1902-12-01", "yyyy-MM-dd")));
        System.out.println(new Gson().toJson(comments));
        System.out.println(comments.get(0));
    }

    public WebType getType() {
        return type;
    }
}
