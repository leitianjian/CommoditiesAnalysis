package com.group.commditiesAnalysis.Utils.WebScrapping;

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

import static com.group.commditiesAnalysis.Utils.WebScrapping.WebUtils.checkURLValid;

public class Search implements WebHeaders{
    // constant field in searching
    private static final String searchWebsiteAddr = "https://s.taobao.com/search?";
    private static final String searchType = "item";
    private static final String charset = "utf8";

    private static final String referPage = "https://www.taobao.com/"; // this refer page should not change
    private String cookies;
    private String requestUrl;
    private String searchParameter;
    private String sortParameter;
    private ArrayList<String> TMCommoditiesList;
    private ArrayList<String> TBCommoditiesList;
    private Map<String, String> headers;

    public Search (String searchParameter, String sortParameter, String cookies){
        this.searchParameter = searchParameter;    // the items you going to search
        this.sortParameter = sortParameter;        // the sort parameter in taobao
        this.cookies = cookies;

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
//        System.out.println(htmlContent);


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
//            for (String x : TMCommoditiesList){
//                System.out.println(x);
//            }
//
//            for (String x : TBCommoditiesList){
//                System.out.println(x);
//            }

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
//        Search s = new Search("cpu", "sale-desc");
    }

}
