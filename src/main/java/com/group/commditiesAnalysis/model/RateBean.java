package com.group.commditiesAnalysis.model;

import com.google.gson.Gson;

import java.util.Date;

public class RateBean {
    private String rateContent;
    private String appendComment;
    private Date uploadDate;

    public RateBean (String rateContent, String appendComment, Date uploadDate){
        this.rateContent = rateContent;
        this.appendComment = appendComment;
        this.uploadDate = uploadDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getAppendComment() {
        return appendComment;
    }

    public String getRateContent() {
        return rateContent;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
