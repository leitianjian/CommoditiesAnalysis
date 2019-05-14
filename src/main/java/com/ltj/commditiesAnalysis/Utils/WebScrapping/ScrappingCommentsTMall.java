package Utils.WebScrapping;

import com.ltj.commditiesAnalysis.model.ItemBean;
import com.ltj.commditiesAnalysis.model.RateBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.WebScrapping.WebUtils.checkURLValid;

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
        Matcher m = Pattern.compile("[0-9]{7,13}").matcher(targetCommodityUrl);
        this.itemID = (m.find()) ? m.group(0) : null;
        this.sellerID = getSellerId();

        this.referPageValue = url;
        this.rateBeans = new ArrayList<>();
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
                return result.toString().substring(8, result.length() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cannot get connection to get the rate json");
            System.exit(1);
        }
        return null;
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
//
//    private int processJsonDataTMall(String data){
//        if (data != null){
//            Gson gson = new Gson();
//            Map JsonMap = gson.fromJson(data, Map.class);
//            int maxPage = ((Double)((Map)JsonMap.get("paginator")).get("lastPage")).intValue();
//
//        }
//    }
    public static void main(String[] args) {
        String targetURL =
                "https://detail.tmall.com/item.htm?id=586008312266&ns=1&abbucket=9";
        String cookie =
                "cna=sDNjFB96hS0CAXQH6vE++4Ka; " +
                        "otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; " +
                        "swfstore=91259; x=__ll%3D-1%26_ato%3D0; ctoken=5sacdDfCClQLGQLGnflGrhllor; " +
                        "hng=CN%7Czh-CN%7CCNY%7C156; dnk=tb865112960; tracknick=tb865112960; " +
                        "lid=tb865112960; lgc=tb865112960; t=f76e3668fae70d52e2eb65a44d3d6660; " +
                        "_tb_token_=6e34593e1615; _m_h5_tk=140171f8ca524e046a0cdad54f0b9cbe_1557581226280; " +
                        "_m_h5_tk_enc=a2199ab44921980e59d0d2b3e63432c8; uc1=cookie16=UIHiLt3xCS3yM2h4eKHS9lpEOw%3D%3D&cookie21=VT5L2FSpccLuJBreK%2BBd&cookie15=W5iHLLyFOGW7aA%3D%3D&existShop=false&pas=0&cookie14=UoTZ48D4%2BF%2FbBA%3D%3D&tag=8&lng=zh_CN; uc3=vt3=F8dBy3qKUB1HNtQxlYM%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; _l_g_=Ug%3D%3D; ck1=\"\"; unb=3339063457; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; login=true; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; _nk_=tb865112960; uss=\"\"; csg=6d0d026c; skt=9e6382cd1f67d722; cq=ccp%3D0; pnm_cku822=098%23E1hvZvvUvbpvUvCkvvvvvjiPRLSZzjtPPLMOsj1VPmPZQjECRLcw1j3vPFSUQjnbmphvLvC50QvjOrjxAfyp%2B3%2BuaNoxfBuKfvDrQjc6eCOiHWLp0n97RAYVyO2vqbVQWl4vARFE%2BFIlBqevD70fd56Ofw1lff8rwmll%2BExreuTivpvUvvmvnJXpfEuEvpvVmvvCvcahKphv8vvvvUlvpv2WvvvCWZCvmjOvvUUdphvWvvvv9iHvpvkcvvmmyZCvmEwCvpvVvvpvvhCv2QhvCvvvMMGtvpvhvvvvv8wCvvpvvUmm; whl=-1%260%260%260; l=bBOkPLk7vicnttcSBOCiZZM1SvbOdIRvDuWX56Vyi_5pe_TsLD7OlLr0dE96Vj5R_b8B474xfKe9-etei; isg=BA4O3m-O3q-hE2273K1jrVzyX-3amp9Q3mSA0jhXVJHJm671oB1_mUhZ04dS18qh";
        ScrappingCommentsTMall s = new ScrappingCommentsTMall(targetURL, cookie);
        System.out.println(s.getRateJson("https://rate.tmall.com/list_detail_rate.htm?itemId=586008312266&sellerId=2578685019&currentPage=1"));
    }
    /**
     *  searchParameter the keyword you want to search
     *  sortParameter   the sort method you want to list the value
     * */
    public ScrappingCommentsTMall() {


    }

    private int scrappingTMComments (String url){
        return 1;
    }

    public ArrayList<RateBean> getRate() {
        return null;
    }

    public ItemBean getResult() {
        return null;
    }

}
