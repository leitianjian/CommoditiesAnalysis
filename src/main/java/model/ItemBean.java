package model;

import Utils.WebScrapping.CommentScrapping;
import Utils.WebScrapping.ScrappingCommentsTB;
import Utils.WebScrapping.ScrappingCommentsTMall;
import static Utils.WebScrapping.CommentScrapping.getCookies;

import java.io.IOException;
import java.util.ArrayList;



public class ItemBean {
    private String id;
    private WebType type;
    private String url;
    private ArrayList<RateBean> comments;

    public ItemBean (String url, String id, WebType type) {
        this.url = url;
        this.id = id;
        this.type = type;
        switch (type){
            case taobao:
                ScrappingCommentsTB s1 = new ScrappingCommentsTB(url, getCookies());
                this.comments = s1.getRate();
                break;
            case tmall:
                ScrappingCommentsTMall s2 = new ScrappingCommentsTMall(url, getCookies());
//                this.comments = s2.getRate();
                break;
        }
    }

    public ArrayList<RateBean> getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return url + "\n" + id + " " + type + comments + "\n";
    }

    public void setCommentsType (){

    }
}
