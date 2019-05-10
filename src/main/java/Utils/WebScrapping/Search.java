package Utils.WebScrapping;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.WebScrapping.WebUtils.checkURLValid;

public class Search implements WebHeaders{
    // constant field in searching
    private static final String searchWebsiteAddr = "https://s.taobao.com/search?";
    private static final String searchType = "item";
    private static final String charset = "utf8";

    private static final String referPage = "https://www.taobao.com/"; // this refer page should not change
    private static String cookies =
            "t=f76e3668fae70d52e2eb65a44d3d6660; v=0; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; x5sec=7b227365617263686170703b32223a226366643437613432623465393066313463646462356530326462643135373961434b756e74755946454e6a366b5a436c6d72714e55686f4d4d7a4d7a4f5441324d7a51314e7a7379227d; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; swfstore=274999; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; unb=3339063457; sg=078; _l_g_=Ug%3D%3D; skt=3e807062b9e6fc2a; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; csg=6076751e; uc3=vt3=F8dByEaySDq%2B3LqznqQ%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=V32FPkk%2Fw0dUvg%3D%3D; existShop=MTU1Njk3NjY1Mg%3D%3D; _cc_=UIHiLt3xSw%3D%3D; dnk=tb865112960; _nk_=tb865112960; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; mt=ci=1_1&np=; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; alitrackid=login.taobao.com; lastalitrackid=login.taobao.com; JSESSIONID=A829FF448B98B24C940D500291153AA5; whl=-1%260%260%261556976975792; l=bBr6By6evaSh5IAZBOCNSuI8LMbOSIRAguPRwCqki_5dZ6T_T-QOlBw9eF96Vj5R_AYB474xfKe9-etFw; uc1=cookie16=WqG3DMC9UpAPBHGz5QBErFxlCA%3D%3D&cookie21=UIHiLt3xThH8t7YQoFNq&cookie15=UtASsssmOIJ0bQ%3D%3D&existShop=false&pas=0&cookie14=UoTZ4tqbv2zC2w%3D%3D&tag=8&lng=zh_CN; isg=BMjIrUdyACYrmWuJhm6LxECNmT8a2S2wNOIGQIJ5FcM2XWrHLoH8C15f0TUt7eRT";
    private String requestUrl;
    private String searchParameter;
    private String sortParameter;
    private ArrayList<String> TMCommoditiesList;
    private ArrayList<String> TBCommoditiesList;
    private Map<String, String> headers;

    public Search (String searchParameter, String sortParameter){
        this.searchParameter = searchParameter;    // the items you going to search
        this.sortParameter = sortParameter;        // the sort parameter in taobao

        // add headers to simulate the login request;
        this.headers = new HashMap<String, String>();
        headers.put(USER_AGENT_KEY, USER_AGENT_VALUE);
        headers.put(ACCEPT_KEY, ACCEPT_VALUE);
        headers.put(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE);
        headers.put(REFER_PAGE_KEY, referPage);
        headers.put(COOKIE_KEY, cookies);

        // final requestUrl;
        this.requestUrl = searchWebsiteAddr +
                "q=" + searchParameter + "&search_type=" + searchType +
                "&ie=" + charset +
                ((sortParameter == null) ? "" : "&sort=") + sortParameter;
        TMCommoditiesList = new ArrayList<String>();
        TBCommoditiesList = new ArrayList<String>();

        initialCommoditiesSiteList();
    }

    /**
     * @param htmlContent the search result page
     * */
    private void getCommoditiesAddr (String htmlContent){
        String regString1 = "g_page_config = \\{.*;";
        Pattern p = Pattern.compile(regString1);
        String regString2 = "\"detail_url\":\".*?\"";
        Pattern p2 = Pattern.compile(regString2);

        Matcher m = p.matcher(htmlContent);
        String temp = "";
        if (m.find()){
            temp = m.group(0);
//            System.out.println(temp);
        } else {
            System.out.println("Please check if the html is got correctly");
            System.exit(2);
        }
//        System.out.println("----------------------------------------------------");
        Matcher m2 = p2.matcher(temp);
        while (m2.find()){
            temp = m2.group(0);
            if (temp.matches(".*:\"//[id].*")) {
                temp = formatWebsiteAddr(temp);

                if (checkURLValid(temp)) {
                    classifyAddr(temp);
                }
            }
        }
    }

    private String formatWebsiteAddr (String webAddr){
        String prefix = "https:";
        webAddr = StringEscapeUtils.unescapeJava(webAddr);

        return prefix + webAddr.substring(0, webAddr.length() - 1).substring(14);
    }

    private void classifyAddr (String webAddr){
//        System.out.println(webAddr);
        if (webAddr.matches(".*taobao.*")){
            TBCommoditiesList.add(webAddr.substring(0, webAddr.length() - 7));
        }
        if (webAddr.matches(".*tmall.*")) {
            TMCommoditiesList.add(webAddr);
        }
    }
    private void initialCommoditiesSiteList () {
        Document doc = null;
        int i = 0;
        while (i < 3) {
            try {
                i += 3;
                Connection con = Jsoup.connect(requestUrl);
                con.headers(headers);
                doc = con.get();
            } catch (MalformedURLException mURLe){
                System.out.println("Wrong website address");
            } catch (HttpStatusException he){
                System.out.println("Http server return error");
            } catch (SocketTimeoutException ste){
                System.out.println("Unavailable network connection re-trying");
                i -= 2;
                System.out.println(i + "th time re-connection");
            } catch (IOException e){
                e.getMessage();
            }
        }
//        System.out.println(doc);
//        System.out.println("----------------------------------------------------");

        if (doc != null){
            getCommoditiesAddr(doc.toString());
            for (String x : TMCommoditiesList){
                System.out.println(x);
            }

            for (String x : TBCommoditiesList){
                System.out.println(x);
            }

        } else {
            System.out.println("The doc is empty, maybe some error in getting connection or request resource");
            System.exit(2);
        }
    }

    public ArrayList<String> getTBCommoditiesList() {
        return TBCommoditiesList;
    }

    public ArrayList<String> getTMCommoditiesList() {
        return TMCommoditiesList;
    }

    public static void main(String[] args) throws IOException{
        Search s = new Search("cpu", "sale-desc");
    }

}
