package Utils.WebScrapping;

import com.ltj.commditiesAnalysis.model.ItemBean;
import com.ltj.commditiesAnalysis.model.RateBean;

import java.util.ArrayList;

public interface ScrappingComments {
    public ArrayList<RateBean> getRate();
    public ItemBean getResult();
}
