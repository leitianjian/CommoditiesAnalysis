import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class testScrappingTBComments {
    private static final String USER_AGENT_VALUE = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/73.0.3683.86 Chrome/73.0.3683.86 Safari/537.36";
    private static final String ACCEPT_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
    private static final String ACCEPT_ENCODING_VALUE = "gzip, deflate, br";
    private static String referPage =
            "https://item.taobao.com/item.htm?id=581723516301&ns=1&abbucket=9";
    private static String cookies =
            "t=f76e3668fae70d52e2eb65a44d3d6660; v=0; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; dnk=tb865112960; mt=ci=1_1&np=; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; skt=752e83d8916307ac; csg=fe5d4661; uc3=vt3=F8dByEaxFEDE7EHXq4U%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=URm48syIIVrSKA%3D%3D; existShop=MTU1NzA3MDY1MA%3D%3D; _cc_=V32FPkk%2Fhw%3D%3D; whl=-1%260%260%261557070792517; _m_h5_tk=a16a92c9e40ef88421d80faa6d4685bf_1557305403825; _m_h5_tk_enc=f554ef6e63995010cf61f45a9417d5fc; x5sec=7b22726174656d616e616765723b32223a223665663232313366346564363730366464316335623130383539343638303033434a7a37796559464549364c6f625754707561346867453d227d; UM_distinctid=16a963bf3363ae-05f94fd8f3845d-24414032-100200-16a963bf3373c5; uc1=cookie14=UoTZ48eDWkbFCg%3D%3D&lng=zh_CN&cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&existShop=false&cookie21=U%2BGCWk%2F7p4mBoUyS4E9C&tag=8&cookie15=UtASsssmOIJ0bQ%3D%3D&pas=0; l=bBr6By6evaSh56CWBOfGIZM1SvbtnIOb8-VPhf5nQICP9QfMrb4PWZ94Hb8HC3GVa6x9R3Pukc9YBlYUGy4oh; isg=BLGxavd6SZK9SeKyt3FSX6GqwDRBi2jJ9SXPm5PHq3i-utAM2--h4Xic3A55Sb1I";

    public static void main(String[] args) throws Exception{
        String url =
                "https://rate.taobao.com/feedRateList.htm?auctionNumId=581723516301&userNumId=379822779&currentPageNum=200&orderType=sort_weight";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept_encoding", ACCEPT_ENCODING_VALUE);
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("Cookie", cookies);
        con.setRequestProperty("Refer", referPage);
        con.setRequestProperty("User-Agent", USER_AGENT_VALUE);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        String temp = null;

        while ((temp = in.readLine()) != null){
            System.out.println(temp);
        }
    }


}
