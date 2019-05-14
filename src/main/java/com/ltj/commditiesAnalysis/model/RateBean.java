package com.ltj.commditiesAnalysis.model;

import java.util.ArrayList;
import java.util.Date;

public class RateBean {
    private String rateContent;
    private ArrayList<String> appendComment;
    private Date uploadDate;

    public RateBean (String rateContent, ArrayList<String> appendComment, Date uploadDate){
        this.rateContent = rateContent;
        this.appendComment = appendComment;
        this.uploadDate = uploadDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public ArrayList<String> getAppendComment() {
        return appendComment;
    }

    public String getRateContent() {
        return rateContent;
    }

    @Override
    public String toString(){
        return "date" + "=" + uploadDate + "rateContent" + rateContent + ", " + appendComment + "\n";
    }
}
