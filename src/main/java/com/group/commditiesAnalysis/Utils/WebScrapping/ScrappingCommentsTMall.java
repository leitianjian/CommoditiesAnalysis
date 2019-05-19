package com.group.commditiesAnalysis.Utils.WebScrapping;

import com.google.gson.Gson;
import com.group.commditiesAnalysis.model.ItemBean;
import com.group.commditiesAnalysis.model.RateBean;
import com.group.commditiesAnalysis.model.WebType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.group.commditiesAnalysis.Utils.WebScrapping.WebUtils.checkURLValid;
import static com.group.commditiesAnalysis.Utils.WebScrapping.WebUtils.getItemId;
import static com.group.commditiesAnalysis.Utils.infoProcess.DateProcess.getDate;

public class ScrappingCommentsTMall implements ScrappingComments, WebHeaders{
    private final String URL_HOST = "https://rate.tmall.com/list_detail_rate.htm?";
    private final String ITEM_ID_KEY = "itemId";
    private final String SELLER_ID_KEY = "sellerId";
    private final String CURRENT_PAGE_KEY = "currentPage";
    private final String ACCEPT_LANG_VALUE = "zh-CN,zh;q=0.9";

    private String targetCommodityUrl;
    private String itemID;
    private String sellerID;

    private String referPageValue;
    private String cookie;

    private ArrayList<RateBean> rateBeans;

    public ScrappingCommentsTMall (String url, String cookie){
        this.targetCommodityUrl = url;
        this.cookie = cookie;
        this.itemID = getItemId(targetCommodityUrl);
        this.sellerID = getSellerId();

        this.referPageValue = url;
        this.rateBeans = new ArrayList<>();
        scrappingAllComments();
    }

    private String getSellerId (){
        HttpURLConnection con = getCon(targetCommodityUrl);
        String sellerId = null;
        if (con != null){
            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), Charset.forName("utf-8")));
                String temp;
                while ((temp = br.readLine()) != null){
                    if (!temp.isEmpty()){
                        if (temp.matches(".*meta\\sname=\"micro.*")){
                            String t1 = temp.split("userid=")[1];
                            sellerId = t1.split(";")[0].trim();
                            System.out.println(sellerId);
                        }
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Cannot get connection to get the seller id");
            System.exit(1);
        }
        return sellerId;
    }

    private String getRateJson (String rateUrl){
        HttpURLConnection con = getCon(rateUrl);
        if (con != null) {
            String temp;
            StringBuilder result = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), Charset.forName("utf-8")));
                while ((temp = br.readLine()) != null){
                    if (!temp.isEmpty()){
                        result.append(temp);
                    }
                }
                String resultStr = result.toString();



//                System.out.println(resultStr);



                if (resultStr.matches(".*rateDetail.*")){
                    return resultStr.substring(9, result.length() - 1);
                } else {
                    return null;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
//                System.out.println(e.getStackTrace()[0]);
//                e.printStackTrace();
            }
        } else {
            System.out.println("Cannot get connection to get the rate json");
            System.exit(1);
        }
        return null;
    }

    private boolean processJsonDataTMall (String data){
//        System.out.println(data);



        if (data != null){
            Gson gson = new Gson();
            Map layout1 = gson.fromJson(data, Map.class);
            Map layout2 = (Map) layout1.get("rateDetail");
            Map paginator = (Map) layout2.get("paginator");
            if (paginator != null) {
                int lastPage = ((Double) paginator.get("lastPage")).intValue();
                int currentPage = ((Double) paginator.get("page")).intValue();
                System.out.println("Scrapping pages " + currentPage + " in max " + lastPage);

//            System.out.println(lastPage);
//            System.out.println(currentPage);


                ArrayList rateList = (ArrayList) layout2.get("rateList");
                for (Object x : rateList) {
                    Map rate = (Map) x;
                    if ((boolean) rate.get("useful")) {
                        String content = (String) rate.get("rateContent");
                        if (!content.matches(".*默认好评.*") && !content.matches(".*没有填写评价.*")) {
                            String dateStr = (String) rate.get("rateDate");
                            Date date = getDate(dateStr, "yyyy-MM-dd HH:mm:ss");

                            Map appendCommentMap = (Map) rate.get("appendComment");

                            String appendComment = null;
                            if (appendCommentMap != null) {
                                appendComment = (String) ((Map) rate.get("appendComment")).get("content");
                            }
                            RateBean rateBean = new RateBean(content, appendComment, date);
                            rateBeans.add(rateBean);
                        }
                    }
                }
                return lastPage != currentPage;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private HttpURLConnection getCon (String targetURL){
        try {
            if (checkURLValid(targetURL)){
                URL obj = new URL(targetURL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty(ACCEPT_KEY, ACCEPT_VALUE);
                con.setRequestProperty(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE);
                con.setRequestProperty(ACCEPT_LANG_KEY, ACCEPT_LANG_VALUE);
                con.setRequestProperty(COOKIE_KEY, cookie);
                con.setRequestProperty(USER_AGENT_KEY, USER_AGENT_VALUE);
                return con;
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void scrappingAllComments (){
        int currentPage = 1;
        String rateUrl, rateJson;
        do {
            rateUrl = URL_HOST + ITEM_ID_KEY + "=" + itemID + "&"
                    + SELLER_ID_KEY + "=" + sellerID + "&"
                    + CURRENT_PAGE_KEY + "=" + currentPage;
            rateJson = getRateJson(rateUrl);
            ++ currentPage;
            try {
                Thread.sleep((int) (3000 * Math.random() + 1000));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        } while (processJsonDataTMall(rateJson));
    }


    public static void main(String[] args) {
        String targetURL =
                "https://detail.tmall.com/item.htm?id=586008312266&ns=1&abbucket=9";
        String cookie =
                "otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; cna=9a+LFI0VKW8CATsovZPkpOuK; t=5413b32d2671eb2636342073d8a8bb40; _tb_token_=f66f3e3818eb; cookie2=3a7c3ce9191957500977ba12d1b8042d; dnk=tb865112960; tracknick=tb865112960; lid=tb865112960; lgc=tb865112960; _m_h5_tk=9f5e2cd3cce97511cb98f74c3f248237_1558107237200; _m_h5_tk_enc=428cea208207e3ae461b7fb34c3bd1a5; hng=CN%7Czh-CN%7CCNY%7C156; uc1=cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D&cookie21=URm48syIYB3rzvI4Dim4&cookie15=W5iHLLyFOGW7aA%3D%3D&existShop=false&pas=0&cookie14=UoTZ7HQZ9F5z3A%3D%3D&tag=8&lng=zh_CN; uc3=vt3=F8dBy3qNPObzPat6VKU%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; _l_g_=Ug%3D%3D; ck1=\"\"; unb=3339063457; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; login=true; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; _nk_=tb865112960; uss=\"\"; csg=914572cb; skt=da1095df9123db1d; x=__ll%3D-1%26_ato%3D0; whl=-1%260%260%260; x5sec=7b22726174656d616e616765723b32223a223463376337663764633436616139656461366332363232656234303531303436434961432b2b594645495441377066796764475850686f4d4d7a4d7a4f5441324d7a51314e7a7378227d; l=bBTeq6BVvxQyzWJhBOCwquI8LkbtcIRfguPRwCY6i_5wy_L1oG_OlpkhqEp6Vj5P97YB44yXh7etbecL5r9f.; isg=BMHBMLT7ec4OOJLBMXX-g0mc0AQbRjQDFAVnxyMW2kglCuDcaz8QsoUM7D7pAs0Y";
//                "otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; cna=9a+LFI0VKW8CATsovZPkpOuK; t=5413b32d2671eb2636342073d8a8bb40; _tb_token_=f66f3e3818eb; cookie2=3a7c3ce9191957500977ba12d1b8042d; dnk=tb865112960; tracknick=tb865112960; lid=tb865112960; lgc=tb865112960; _m_h5_tk=9f5e2cd3cce97511cb98f74c3f248237_1558107237200; _m_h5_tk_enc=428cea208207e3ae461b7fb34c3bd1a5; hng=CN%7Czh-CN%7CCNY%7C156; uc1=cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D&cookie21=URm48syIYB3rzvI4Dim4&cookie15=W5iHLLyFOGW7aA%3D%3D&existShop=false&pas=0&cookie14=UoTZ7HQZ9F5z3A%3D%3D&tag=8&lng=zh_CN; uc3=vt3=F8dBy3qNPObzPat6VKU%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; _l_g_=Ug%3D%3D; ck1=\"\"; unb=3339063457; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; login=true; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; _nk_=tb865112960; uss=\"\"; csg=914572cb; skt=da1095df9123db1d; whl=-1%260%260%260; x=__ll%3D-1%26_ato%3D0; l=bBTeq6BVvxQyzso8BOCwquI8LkbOcIRfguPRwCY6i_5dT_8y92bOlprjmEJ6Vj5Pth8B44yXh7eTNe0T5rMf.; isg=BLS08zuYVDl1D8fOXLYjVKyPhXvmJdmUUdbSiE4VLz_ouVUDdpySBTQ_ObHEQRDP";

        ScrappingCommentsTMall s = new ScrappingCommentsTMall(targetURL, cookie);
        s.scrappingAllComments();
        for (RateBean r : s.rateBeans){
            System.out.println(r);
        }
        System.out.println(s.rateBeans.size());
//        System.out.println(s.getRateJson("https://rate.tmall.com/list_detail_rate.htm?itemId=586008312266&sellerId=2578685019&currentPage=38"));
    }

    public ArrayList<RateBean> getRate() {
        return rateBeans;
    }

    public ItemBean getResult() {
        return new ItemBean(targetCommodityUrl, itemID, WebType.tmall, rateBeans);
    }

}
