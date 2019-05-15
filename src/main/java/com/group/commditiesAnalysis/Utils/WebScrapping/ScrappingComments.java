package com.group.commditiesAnalysis.Utils.WebScrapping;

import com.group.commditiesAnalysis.model.ItemBean;
import com.group.commditiesAnalysis.model.RateBean;

import java.util.ArrayList;

public interface ScrappingComments {
    public ArrayList<RateBean> getRate();
    public ItemBean getResult();
}
