package Utils.WebScrapping;

import model.ItemBean;
import model.RateBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.WebScrapping.WebUtils.checkURLValid;

public class ScrappingCommentsTB implements ScrappingComments, WebHeaders {
    private final String URL_HOST = "https://rate.taobao.com/feedRateList.htm?";
    private final String USER_NUM_ID_KEY = "userNumId";
    private final String USER_NUM_ID_VALUE = "379822779";
    private final String AUCTION_NUM_ID_KEY = "auctionNumId";
    private final String CURRENT_PAGE_KEY = "currentPageNum";
    private final String ORDER_TYPE_KEY = "orderType";
    private String orderType = "sort_weight";

    private String targetCommodityUrl;
    private String rateUrl;
    private String itemID;

    private String referPageValue;
    private String cookie;

    private int totalPages;
    private int nowPage = 2;

    public ScrappingCommentsTB (String url, String cookie){
        this.targetCommodityUrl = url;
        this.cookie = cookie;
        Matcher m = Pattern.compile("[0-9]{7,13}").matcher(targetCommodityUrl);
        this.itemID = (m.find()) ? m.group(0) : null;
        System.out.println(itemID);
        this.referPageValue = url;

        this.rateUrl = URL_HOST + AUCTION_NUM_ID_KEY + "=" + itemID +
                "&" + USER_NUM_ID_KEY + "=" + USER_NUM_ID_VALUE +
                "&" + CURRENT_PAGE_KEY + "=" + nowPage +
                "&" + ORDER_TYPE_KEY + "=" + orderType;
        System.out.println(rateUrl);
    }



    private String getRateJson () {
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
                return result.toString();
            } else {
                System.out.println("Invalid URL");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        String targetURL =
                "https://item.taobao.com/item.htm?id=581723516301&ns=1&abbucket=9";
        String cookie =
                "t=f76e3668fae70d52e2eb65a44d3d6660; v=0; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; dnk=tb865112960; mt=ci=1_1&np=; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; skt=752e83d8916307ac; csg=fe5d4661; uc3=vt3=F8dByEaxFEDE7EHXq4U%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=URm48syIIVrSKA%3D%3D; existShop=MTU1NzA3MDY1MA%3D%3D; _cc_=V32FPkk%2Fhw%3D%3D; whl=-1%260%260%261557070792517; _m_h5_tk=a16a92c9e40ef88421d80faa6d4685bf_1557305403825; _m_h5_tk_enc=f554ef6e63995010cf61f45a9417d5fc; x5sec=7b22726174656d616e616765723b32223a223665663232313366346564363730366464316335623130383539343638303033434a7a37796559464549364c6f625754707561346867453d227d; UM_distinctid=16a963bf3363ae-05f94fd8f3845d-24414032-100200-16a963bf3373c5; uc1=cookie14=UoTZ48eDWkbFCg%3D%3D&lng=zh_CN&cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&existShop=false&cookie21=U%2BGCWk%2F7p4mBoUyS4E9C&tag=8&cookie15=UtASsssmOIJ0bQ%3D%3D&pas=0; l=bBr6By6evaSh56CWBOfGIZM1SvbtnIOb8-VPhf5nQICP9QfMrb4PWZ94Hb8HC3GVa6x9R3Pukc9YBlYUGy4oh; isg=BLGxavd6SZK9SeKyt3FSX6GqwDRBi2jJ9SXPm5PHq3i-utAM2--h4Xic3A55Sb1I";
        ScrappingCommentsTB s = new ScrappingCommentsTB(targetURL, cookie);
        System.out.println(s.getRateJson());
    }

    public RateBean[] getRate() {
        return new RateBean[0];
    }

    public ItemBean getResult (){
        return null;
    }

}
