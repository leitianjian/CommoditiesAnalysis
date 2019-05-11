import Utils.WebScrapping.ScrappingCommentsTB;
import com.google.gson.Gson;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class testJson {
    public static void writeToFile () throws IOException{
        String targetURL =
                "https://item.taobao.com/item.htm?id=581723516301&ns=1&abbucket=9";
        String cookie =
                "t=f76e3668fae70d52e2eb65a44d3d6660; v=0; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; dnk=tb865112960; mt=ci=1_1&np=; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; skt=752e83d8916307ac; csg=fe5d4661; uc3=vt3=F8dByEaxFEDE7EHXq4U%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=URm48syIIVrSKA%3D%3D; existShop=MTU1NzA3MDY1MA%3D%3D; _cc_=V32FPkk%2Fhw%3D%3D; whl=-1%260%260%261557070792517; _m_h5_tk=a16a92c9e40ef88421d80faa6d4685bf_1557305403825; _m_h5_tk_enc=f554ef6e63995010cf61f45a9417d5fc; x5sec=7b22726174656d616e616765723b32223a223665663232313366346564363730366464316335623130383539343638303033434a7a37796559464549364c6f625754707561346867453d227d; UM_distinctid=16a963bf3363ae-05f94fd8f3845d-24414032-100200-16a963bf3373c5; uc1=cookie14=UoTZ48eDWkbFCg%3D%3D&lng=zh_CN&cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&existShop=false&cookie21=U%2BGCWk%2F7p4mBoUyS4E9C&tag=8&cookie15=UtASsssmOIJ0bQ%3D%3D&pas=0; l=bBr6By6evaSh56CWBOfGIZM1SvbtnIOb8-VPhf5nQICP9QfMrb4PWZ94Hb8HC3GVa6x9R3Pukc9YBlYUGy4oh; isg=BLGxavd6SZK9SeKyt3FSX6GqwDRBi2jJ9SXPm5PHq3i-utAM2--h4Xic3A55Sb1I";
        ScrappingCommentsTB s = new ScrappingCommentsTB(targetURL, cookie);
//        String targetJson = s.getRateJson();

        try(PrintWriter out = new PrintWriter(new FileOutputStream("targetJson.txt"))){
//            out.println(targetJson);
            out.flush();
            out.close();
        }
    }

    public static String readFile () throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("targetJson.txt"), "utf-8"));
        return br.readLine();
    }

    public static int getMaxPage (Map data){
        return ((Double) data.get("maxPage")).intValue();
    }

    public static ArrayList getComments (String data){
        Gson gson = new Gson();
        Map rootMap = gson.fromJson(data, HashMap.class);
        return (ArrayList)rootMap.get("comments");
    }

    public static ArrayList<String> getAppendComments (ArrayList appendList){
        ArrayList<String> appendContents = new ArrayList<>();
        for (Object x : appendList){
            appendContents.add((String)((Map) x).get("content"));
        }
        return appendContents;
    }

    public static Date getDate (String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date date = sdf.parse(strDate);
            return date;
        } catch (ParseException e){
            System.out.println("Parse failure!");
            return null;
        }
    }

    public static void processJsonDataTB (String data){
        if (data != null){
            ArrayList comments = getComments(data);
            for (Object m : comments) {
                String dateStr = (String)((Map) m).get("date");
                Date date = getDate(dateStr);
                String content = (String)((Map) m).get("content");
                System.out.println(dateStr);
                System.out.println(date);
                System.out.println(content);
                ArrayList appendList = (ArrayList)(((Map) m).get("appendList"));

                if (appendList.size() != 0){
                    System.out.println(appendList);
                }
//                if (!(((Map) m).get("append")).equals("null")){
//                    System.out.println(appendList);
//                }
            }

        }
    }

    public static void main(String[] args) throws IOException {
//        writeToFile();
        String targetJson = readFile();
        targetJson = targetJson.substring(1, targetJson.length() - 1);
        Gson g = new Gson();
        System.out.println(targetJson);
        processJsonDataTB(targetJson);
    }
}
