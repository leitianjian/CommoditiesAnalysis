package Utils.WebScrapping;

import model.ItemBean;
import model.RateBean;

import java.util.ArrayList;

public interface ScrappingComments {
    public ArrayList<RateBean> getRate();
    public ItemBean getResult();
}
