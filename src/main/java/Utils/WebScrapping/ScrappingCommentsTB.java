package Utils.WebScrapping;

import com.google.gson.Gson;
import model.ItemBean;
import model.RateBean;
import model.WebType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.WebScrapping.WebUtils.checkURLValid;
import static Utils.infoProcess.DateProcess.getDate;

public class ScrappingCommentsTB implements ScrappingComments, WebHeaders {
    private final String URL_HOST = "https://rate.taobao.com/feedRateList.htm?";
    private final String USER_NUM_ID_KEY = "userNumId";
    private final String USER_NUM_ID_VALUE = "379822779";
    private final String AUCTION_NUM_ID_KEY = "auctionNumId";
    private final String CURRENT_PAGE_KEY = "currentPageNum";
    private final String ORDER_TYPE_KEY = "orderType";
    private String orderType = "sort_weight";

    private String targetCommodityUrl;
//    private String rateUrl;
    private String itemID;

    private String referPageValue;
    private String cookie;

    private ArrayList<RateBean> rateBeans;

    public ScrappingCommentsTB (String url, String cookie){
        this.targetCommodityUrl = url;
        this.cookie = cookie;
        // get the items id
        Matcher m = Pattern.compile("[0-9]{7,13}").matcher(targetCommodityUrl);
        this.itemID = (m.find()) ? m.group(0) : null;
        // set refer page as the detail page
        this.referPageValue = url;
        rateBeans = new ArrayList<>();
    }

    public ArrayList<String> getAppendComments (ArrayList appendList){
        ArrayList<String> appendContents = new ArrayList<>();
        for (Object x : appendList){
            appendContents.add((String)((Map) x).get("content"));
        }
        return appendContents;
    }

    /**
     * Json processing and get the value i want
     * @param  data the value after get the rate json in target url
     * @return when maxPage = currentPage return false*/
    public boolean processJsonDataTB (String data){
        if (data != null){
            Gson gson = new Gson();
            Map JsonMap = gson.fromJson(data, HashMap.class);
            int maxPage = ((Double) JsonMap.get("maxPage")).intValue();
            int currentPage = ((Double) JsonMap.get("currentPageNum")).intValue();
            if (maxPage != currentPage) {
                ArrayList comments = (ArrayList) JsonMap.get("comments");
                for (Object m : comments) {
                    String content = (String) ((Map) m).get("content");
                    // a small filter to filter useless comments
                    // it may require a more advanced filter
                    if (!content.matches(".*默认好评!.*") && !content.matches(".*没有填写评价.*")) {
                        String dateStr = (String) ((Map) m).get("date");
                        Date date = getDate(dateStr);

                        ArrayList appendList = (ArrayList) (((Map) m).get("appendList"));
                        RateBean rate = new RateBean(content, getAppendComments(appendList), date);
                        rateBeans.add(rate);
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Input data is null");
            return false;
        }
    }

    /**
     * @param rateUrl target url which consist the feedBack list
     * @return return Json result to method <code> processingJsonDataTB </code>
     * */
    private String getRateJson (String rateUrl) {
        try {
            if (checkURLValid(rateUrl)) {
                URL obj = new URL(rateUrl);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE);
                con.setRequestProperty(ACCEPT_KEY, "*/*");
                con.setRequestProperty(COOKIE_KEY, cookie);
                con.setRequestProperty(REFER_PAGE_KEY, referPageValue);
                con.setRequestProperty(USER_AGENT_KEY, USER_AGENT_VALUE);


                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), Charset.forName("utf-8")));
                String temp;
                StringBuilder result = new StringBuilder();
                while ((temp = br.readLine()) != null){
                    if (!temp.isEmpty()) {
                        result.append(temp);
                    }
                }
                return result.toString().substring(1, result.length() - 1);
            } else {
                System.out.println("Invalid URL");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * The loop to turn page of one item
     * */
    private void scrappingAllComments (){
        int currentPage = 1;
        String rateUrl;
        String rateJson;
        do{
            rateUrl = URL_HOST + AUCTION_NUM_ID_KEY + "=" + itemID +
                    "&" + USER_NUM_ID_KEY + "=" + USER_NUM_ID_VALUE +
                    "&" + CURRENT_PAGE_KEY + "=" + currentPage +
                    "&" + ORDER_TYPE_KEY + "=" + orderType;
            rateJson = getRateJson(rateUrl);
            ++ currentPage;
            try {
                Thread.sleep((int) (2000 * Math.random()));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        } while (processJsonDataTB(rateJson));

    }


    public static void main(String[] args) {
        String targetURL =
                "https://item.taobao.com/item.htm?id=581723516301&ns=1&abbucket=9";
        String cookie =
                "t=f76e3668fae70d52e2eb65a44d3d6660; v=0; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; dnk=tb865112960; mt=ci=1_1&np=; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; skt=752e83d8916307ac; csg=fe5d4661; uc3=vt3=F8dByEaxFEDE7EHXq4U%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=URm48syIIVrSKA%3D%3D; existShop=MTU1NzA3MDY1MA%3D%3D; _cc_=V32FPkk%2Fhw%3D%3D; whl=-1%260%260%261557070792517; _m_h5_tk=a16a92c9e40ef88421d80faa6d4685bf_1557305403825; _m_h5_tk_enc=f554ef6e63995010cf61f45a9417d5fc; x5sec=7b22726174656d616e616765723b32223a223665663232313366346564363730366464316335623130383539343638303033434a7a37796559464549364c6f625754707561346867453d227d; UM_distinctid=16a963bf3363ae-05f94fd8f3845d-24414032-100200-16a963bf3373c5; uc1=cookie14=UoTZ48eDWkbFCg%3D%3D&lng=zh_CN&cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&existShop=false&cookie21=U%2BGCWk%2F7p4mBoUyS4E9C&tag=8&cookie15=UtASsssmOIJ0bQ%3D%3D&pas=0; l=bBr6By6evaSh56CWBOfGIZM1SvbtnIOb8-VPhf5nQICP9QfMrb4PWZ94Hb8HC3GVa6x9R3Pukc9YBlYUGy4oh; isg=BLGxavd6SZK9SeKyt3FSX6GqwDRBi2jJ9SXPm5PHq3i-utAM2--h4Xic3A55Sb1I";
        ScrappingCommentsTB s = new ScrappingCommentsTB(targetURL, cookie);
        s.scrappingAllComments();

        for (RateBean r : s.rateBeans){
            System.out.print(r);

        }
    }


    @Override
    public ArrayList<RateBean> getRate() {
        scrappingAllComments();
        return rateBeans;
    }

    public ItemBean getResult (){
        return new ItemBean(targetCommodityUrl, itemID, WebType.taobao);
    }

}
