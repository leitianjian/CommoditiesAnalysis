package Utils.WebScrapping;

import model.ItemBean;
import model.RateBean;

import java.io.IOException;
import java.util.Map;

public class ScrappingCommentsTMall implements ScrappingComments{
    private static String referPageHead = "https://www.taobao.com/";
    private String cookie;
    private Map<String, String> headers;
    private String targetCommodityUrl;

    public ScrappingCommentsTMall (String url, String cookie){
        this.targetCommodityUrl = url;
        this.cookie = cookie;
    }

    /**
     * @param searchParameter the keyword you want to search
     * @param sortParameter   the sort method you want to list the value
     * */
    public ScrappingCommentsTMall() throws IOException{


    }

    private int scrappingTMComments (String url){
        return 1;
    }

    public RateBean[] getRate() {
        return new RateBean[0];
    }

    public ItemBean getResult() {
        return null;
    }

}
