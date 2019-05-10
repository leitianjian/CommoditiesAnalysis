package Utils.WebScrapping;


import model.ItemBean;

import java.util.ArrayList;

public class CommentScrapping {
    private ArrayList<ItemBean> result;
    private String searchItem;
    private String sortParam;

    private ArrayList<String> TBAddrList;
    private ArrayList<String> TMAddrList;

    private static String cookies =
            "t=f76e3668fae70d52e2eb65a44d3d6660; v=0; _tb_token_=6e34593e1615; cna=sDNjFB96hS0CAXQH6vE++4Ka; tracknick=tb865112960; lgc=tb865112960; tg=0; x5sec=7b227365617263686170703b32223a226366643437613432623465393066313463646462356530326462643135373961434b756e74755946454e6a366b5a436c6d72714e55686f4d4d7a4d7a4f5441324d7a51314e7a7379227d; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; swfstore=274999; cookie2=1eb6cf02c0963b3e1c24b50ac3147c45; unb=3339063457; sg=078; _l_g_=Ug%3D%3D; skt=3e807062b9e6fc2a; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; csg=6076751e; uc3=vt3=F8dByEaySDq%2B3LqznqQ%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=V32FPkk%2Fw0dUvg%3D%3D; existShop=MTU1Njk3NjY1Mg%3D%3D; _cc_=UIHiLt3xSw%3D%3D; dnk=tb865112960; _nk_=tb865112960; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; mt=ci=1_1&np=; enc=Bvm1gznwQe12p5Q66FuYZX4uRbUehcEPmQeLAyoDPgr3EoLudb1UiOIQfi2kMX4Sy%2FWXpqxmmLin7VO5bmuKOQ%3D%3D; alitrackid=login.taobao.com; lastalitrackid=login.taobao.com; JSESSIONID=A829FF448B98B24C940D500291153AA5; whl=-1%260%260%261556976975792; l=bBr6By6evaSh5IAZBOCNSuI8LMbOSIRAguPRwCqki_5dZ6T_T-QOlBw9eF96Vj5R_AYB474xfKe9-etFw; uc1=cookie16=WqG3DMC9UpAPBHGz5QBErFxlCA%3D%3D&cookie21=UIHiLt3xThH8t7YQoFNq&cookie15=UtASsssmOIJ0bQ%3D%3D&existShop=false&pas=0&cookie14=UoTZ4tqbv2zC2w%3D%3D&tag=8&lng=zh_CN; isg=BMjIrUdyACYrmWuJhm6LxECNmT8a2S2wNOIGQIJ5FcM2XWrHLoH8C15f0TUt7eRT";

    public CommentScrapping (String searchItem, String sortParam){
        this.searchItem = searchItem;
        this.sortParam = sortParam;
        Search search = new Search(searchItem, sortParam);
        this.TBAddrList = search.getTBCommoditiesList();
        this.TMAddrList = search.getTMCommoditiesList();
        this.result = new ArrayList<ItemBean>();

        for (String addr : TBAddrList){
            ItemBean resultItem = new ScrappingCommentsTB(addr, cookies).getResult();
            if (resultItem != null) {
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
        }

        for (String addr : TMAddrList){
            ItemBean resultItem = new ScrappingCommentsTMall(addr, cookies).getResult();
            if (resultItem != null) {
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
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

    public static String getCookies() {
        return cookies;
    }
}
