package com.group.commditiesAnalysis.Utils.WebScrapping;


import com.google.gson.Gson;
import com.group.commditiesAnalysis.DAO.WriteResult;
import com.group.commditiesAnalysis.model.ItemBean;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CommentScrapping {
    private ArrayList<ItemBean> result;
    private String searchItem;
    private String sortParam;

    private ArrayList<String> TBAddrList;
    private ArrayList<String> TMAddrList;

    private static String cookies1 =
            "t=f76e3668fae70d52e2eb65a44d3d6660; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; swfstore=274999; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; alitrackid=login.taobao.com; UM_distinctid=16a963bf3363ae-05f94fd8f3845d-24414032-100200-16a963bf3373c5; v=0; unb=3339063457; sg=078; _l_g_=Ug%3D%3D; skt=677cf19109b80f42; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; csg=84da28fe; uc3=vt3=F8dBy3vKtn7HXggMP%2F8%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=W5iHLLyFOGW7aA%3D%3D; existShop=MTU1ODQzNDgwMA%3D%3D; _cc_=Vq8l%2BKCLiw%3D%3D; dnk=tb865112960; _nk_=tb865112960; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; mt=ci=1_1; _m_h5_tk=6560007da2cfb87798d96bd8f9daaaa4_1558442016970; _m_h5_tk_enc=8fe5c3e4baec5043e27d0de8f72793f2; miid=126369831941587860; uc1=cookie14=UoTZ7HEHyIOdRw%3D%3D&lng=zh_CN&cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D&existShop=false&cookie21=UIHiLt3xThH8t7YQoFNq&tag=8&cookie15=URm48syIIVrSKA%3D%3D&pas=0; x5sec=7b227365617263686170703b32223a226234343238633639313838663461666265393861366133366433316264343461434f69796a2b6346454d534333354c35397347746767456144444d7a4d7a6b774e6a4d304e5463374d513d3d227d; lastalitrackid=item.taobao.com; JSESSIONID=ADBDA506CA109F53919EA09033CE6D7D; l=bBr6By6evaSh5J7QBOCGSZM1SvbOLIRAguWX56Vyi_5BV6Y_i9bOln09JFv6Vj5R_b8B474xfKe9-etei; isg=BAIC-z-06j4zNvHnyIwRkvbPUwFuxks0guD8rkwbTHUgn6IZNGPs_ZLdT9tGz36F; whl=-1%260%260%261558437742531";
//            "t=f76e3668fae70d52e2eb65a44d3d6660; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; swfstore=274999; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; alitrackid=login.taobao.com; UM_distinctid=16a963bf3363ae-05f94fd8f3845d-24414032-100200-16a963bf3373c5; v=0; unb=3339063457; sg=078; _l_g_=Ug%3D%3D; skt=677cf19109b80f42; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; csg=84da28fe; uc3=vt3=F8dBy3vKtn7HXggMP%2F8%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=W5iHLLyFOGW7aA%3D%3D; existShop=MTU1ODQzNDgwMA%3D%3D; _cc_=Vq8l%2BKCLiw%3D%3D; dnk=tb865112960; _nk_=tb865112960; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; mt=ci=1_1; _m_h5_tk=6560007da2cfb87798d96bd8f9daaaa4_1558442016970; _m_h5_tk_enc=8fe5c3e4baec5043e27d0de8f72793f2; miid=126369831941587860; uc1=cookie14=UoTZ7HEHyIOdRw%3D%3D&lng=zh_CN&cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D&existShop=false&cookie21=UIHiLt3xThH8t7YQoFNq&tag=8&cookie15=URm48syIIVrSKA%3D%3D&pas=0; x5sec=7b227365617263686170703b32223a226234343238633639313838663461666265393861366133366433316264343461434f69796a2b6346454d534333354c35397347746767456144444d7a4d7a6b774e6a4d304e5463374d513d3d227d; whl=-1%260%260%261558436681603; JSESSIONID=462BC074D9472E0475B640A9C6CF4687; lastalitrackid=item.taobao.com; l=bBr6By6evaSh5hfjBOCiIZM1SvbTAIRVguWX56Vyi_5a5681bL7Oln09LFJ6Vj5R_j86474xfKe9-etei; isg=BGFhXzu3mdO0YTKih-GCb5EacCRxG5jZxbV_q8M2bGjOKoD8D1-e0OFojD4J4m04";
//            "cna=sDNjFB96hS0CAXQH6vE++4Ka; otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; x=__ll%3D-1%26_ato%3D0; ctoken=5sacdDfCClQLGQLGnflGrhllor; hng=CN%7Czh-CN%7CCNY%7C156; dnk=tb865112960; tracknick=tb865112960; lid=tb865112960; lgc=tb865112960; t=f76e3668fae70d52e2eb65a44d3d6660; _tb_token_=6e34593e1615; ck1=\"\"; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; skt=9e6382cd1f67d722; uc1=cookie16=UtASsssmPlP%2Ff1IHDsDaPRu%2BPw%3D%3D&cookie21=URm48syIYB3rzvI4Dim4&cookie15=W5iHLLyFOGW7aA%3D%3D&existShop=false&pas=0&cookie14=UoTZ7HEHykbF6Q%3D%3D&tag=8&lng=zh_CN; uc3=vt3=F8dBy3vKtn7HXggMP%2F8%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=W5iHLLyFOGW7aA%3D%3D; _l_g_=Ug%3D%3D; unb=3339063457; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; login=true; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; _nk_=tb865112960; sg=078; csg=84da28fe; sm4=440305; _m_h5_tk=7505bcbe422a92177ebfcfbd58d36d01_1558445028438; _m_h5_tk_enc=e6319fbf40b7d51ae7e6dbc1e9874e67; whl=-1%260%260%260; l=bBOkPLk7vicntqUUBOfTZZM1Svb9CIdflsPPhf5nQICP99XdAmwRWZtm7cx9C3GVa6fBJ3Pukc9YBPTiwPaXl; isg=BG9vJylGbwWUPmw8xUYyInX5_opzAY7vZ1_BlYH96V5Y0IfSiOdVhA_GUoDLqJuu";
//            "t=f76e3668fae70d52e2eb65a44d3d6660; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; UM_distinctid=16a963bf3363ae-05f94fd8f3845d-24414032-100200-16a963bf3373c5; v=0; unb=3339063457; sg=078; _l_g_=Ug%3D%3D; skt=677cf19109b80f42; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; csg=84da28fe; uc3=vt3=F8dBy3vKtn7HXggMP%2F8%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=W5iHLLyFOGW7aA%3D%3D; existShop=MTU1ODQzNDgwMA%3D%3D; _cc_=Vq8l%2BKCLiw%3D%3D; dnk=tb865112960; _nk_=tb865112960; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; mt=ci=1_1; uc1=cookie16=UtASsssmPlP%2Ff1IHDsDaPRu%2BPw%3D%3D&cookie21=URm48syIYB3rzvI4Dim4&cookie15=W5iHLLyFOGW7aA%3D%3D&existShop=false&pas=0&cookie14=UoTZ7HEHykbF6Q%3D%3D&tag=8&lng=zh_CN; _m_h5_tk=6560007da2cfb87798d96bd8f9daaaa4_1558442016970; _m_h5_tk_enc=8fe5c3e4baec5043e27d0de8f72793f2; miid=126369831941587860; whl=-1%260%260%261558435588952; l=bBr6By6evaSh5v48BOCgRZM1SvbTYIR4SuWX56Vyi_5wP1YVq1bOln0Rsev6Vj5PtiTp474xfKeT0era8z8f.; isg=BO7uM1WVPjojJU1b_CDFFnqrP026-v8w_oRgchi3AfGg-4xVgX6f-Bi9syeyJqoB";
    private String cookies;


    // this constructor is used by javaGUI
    public CommentScrapping (ArrayList<String> TBAddrList, ArrayList<String> TMAddrList,
                             String path, String cookies, String searchName) throws InterruptedException{
        this.TBAddrList = TBAddrList;
        this.TMAddrList = TMAddrList;
        this.result = new ArrayList<>();
        this.cookies = cookies;
        path = path + "/result/" + searchName;

        File file = new File (path);
        System.out.println("Target storing directory is " + file.getPath());
        if (!file.exists()){
            System.out.println("Making a new directory named result");
            if (!file.mkdirs()){
                System.out.println("Making result storing directory fail");
                System.exit(2);
            }
        }

        for (String addr : TBAddrList){
            System.out.println("Target commodities address: " + addr);
            ItemBean resultItem = new ScrappingCommentsTB(addr, cookies).getResult();
            if (resultItem != null) {
                WriteResult.writeResult(resultItem, path);
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
        }

        for (String addr : TMAddrList){
            System.out.println("Target commodities address: " + addr);
            ItemBean resultItem = new ScrappingCommentsTMall(addr, cookies).getResult();
            if (resultItem != null) {
                WriteResult.writeResult(resultItem, path);
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
        }
        System.out.println("Scrapping finished");
    }

    public CommentScrapping (String searchItem, String sortParam) throws InterruptedException{
        this.searchItem = searchItem;
        this.sortParam = sortParam;
        Search search = new Search(searchItem, sortParam, cookies1);
        String storePath = null;
        File file = new File("result/" + searchItem);
        if(!file.exists()){
            if (file.mkdir()){
                storePath = file.getPath();
            } else {
                System.out.println("Making result storing directory fail");
                System.exit(2);
            }
        } else {
            storePath = "result/" + searchItem;
        }
        this.TBAddrList = search.getTBCommoditiesList();
        this.TMAddrList = search.getTMCommoditiesList();
        this.result = new ArrayList<>();
//        Gson gson = new Gson();

        for (String addr : TBAddrList){
            System.out.println("Target commodities address: " + addr);
            ItemBean resultItem = new ScrappingCommentsTB(addr, cookies1).getResult();
            if (resultItem != null) {
                WriteResult.writeResult(resultItem, storePath);
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
        }

        for (String addr : TMAddrList){
            System.out.println("Target commodities address: " + addr);
            ItemBean resultItem = new ScrappingCommentsTMall(addr, cookies1).getResult();
            if (resultItem != null) {
                WriteResult.writeResult(resultItem, storePath);
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CommentScrapping cs = new CommentScrapping("办公椅", "sale-desc");
        ArrayList<ItemBean> temp = cs.getResult();
        for (int i = 0; i < temp.size(); ++ i) {
            ItemBean t = temp.get(i);
////            String fileName = "itemID=" + t.getId();
////            try (PrintWriter out = new PrintWriter(new FileOutputStream(fileName))){
////                out.println(t);
////                out.flush();
////                out.close();
////            }
            System.out.println(t);
        }
    }

    public ArrayList<ItemBean> getResult() {
        return result;
    }

    public void setSearchItem(String searchItem) {
        this.searchItem = searchItem;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

//    public static String getCookies() {
//        return cookies;
//    }
}
