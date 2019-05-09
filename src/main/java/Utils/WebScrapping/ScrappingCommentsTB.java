package Utils.WebScrapping;

import model.RateBean;

public class ScrappingCommentsTB implements ScrappingComments {

    private String targetCommodityUrl;

    public ScrappingCommentsTB (String url){
        this.targetCommodityUrl = url;
    }



    public RateBean[] getRate() {
        return new RateBean[0];
    }

}
