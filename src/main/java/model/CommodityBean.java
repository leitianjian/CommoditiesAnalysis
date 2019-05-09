package model;

import Utils.WebScrapping.ScrappingCommentsTB;
import Utils.WebScrapping.ScrappingCommentsTMall;

import java.io.IOException;

enum WebType {
    taobao,  tmall
}

public class CommodityBean {
    private String id;
    private WebType type;
    private String url;
    private RateBean[] comments;

    public CommodityBean (String id, WebType type) throws IOException {
        this.id = id;
        this.type = type;
        switch (type){
            case taobao:
                ScrappingCommentsTB s1 = new ScrappingCommentsTB(url);
                this.comments = s1.getRate();
                break;
            case tmall:
                ScrappingCommentsTMall s2 = new ScrappingCommentsTMall(url);
                this.comments = s2.getRate();
                break;
        }
    }

    public void setCommentsType (){

    }
}
