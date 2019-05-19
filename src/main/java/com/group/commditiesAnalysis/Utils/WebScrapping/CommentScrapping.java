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

    private static String cookies =
            "thw=cn; t=5413b32d2671eb2636342073d8a8bb40; hng=CN%7Czh-CN%7CCNY%7C156; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; cna=9a+LFI0VKW8CATsovZPkpOuK; v=0; cookie2=3a7c3ce9191957500977ba12d1b8042d; _tb_token_=f66f3e3818eb; tracknick=tb865112960; dnk=tb865112960; enc=eyCWMuP7XA7VE0CxA%2FhfPndIi8IXk6TejjUqdbzeNs55vnQuM%2BeHkEi3ZkChazP5zh4ELTqeCGiFzneaSFozrg%3D%3D; lgc=tb865112960; _cc_=Vq8l%2BKCLiw%3D%3D; tg=0; mt=ci=1_1&np=; unb=3339063457; sg=078; _l_g_=Ug%3D%3D; skt=2b93a376ef991bc8; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; csg=2ede5bfa; uc3=vt3=F8dBy3qNPOMPpBQUZBc%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=VT5L2FSpMGV7TQ%3D%3D; existShop=MTU1ODEwNDUzNQ%3D%3D; _nk_=tb865112960; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; uc1=cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D&cookie21=V32FPkk%2FgihF%2FS5nr3O5&cookie15=WqG3DMC9VAQiUQ%3D%3D&existShop=false&pas=0&cookie14=UoTZ7HQZ8R5Ngg%3D%3D&tag=8&lng=zh_CN; x5sec=7b22726174656d616e616765723b32223a226435333066636434313337306337356563623465616537663332323139663936434a47622b2b5946454c547376744b7a6966502f62786f4d4d7a4d7a4f5441324d7a51314e7a7378227d; whl=-1%260%261550899538051%261558105573598; _m_h5_tk=b9147042337d9eb6480f8b7180f85606_1558114584395; _m_h5_tk_enc=c9d8fdcae3f6c4fd4c5f1d8788e97915; l=bBjM3Y14vxOCCXIFBOCZ5uI8Lkb98IRfguPRwCY6i_5I7_Y6fH_Olpk0-Ev6Vj5PtgYB44yXh7eT2ezbWrLf.; isg=BAwM0Yw-vIGvK6_GtyBpAiI23WP-7bEM-Y56gGbNTLdY8a37jlQbfZUHkbnsuehH";

    public CommentScrapping (String searchItem, String sortParam){
        this.searchItem = searchItem;
        this.sortParam = sortParam;
        Search search = new Search(searchItem, sortParam, cookies);
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
            ItemBean resultItem = new ScrappingCommentsTB(addr, cookies).getResult();
            if (resultItem != null) {
                WriteResult.writeResult(resultItem, storePath);
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
        }

        for (String addr : TMAddrList){
            System.out.println("Target commodities address: " + addr);
            ItemBean resultItem = new ScrappingCommentsTMall(addr, cookies).getResult();
            if (resultItem != null) {
                WriteResult.writeResult(resultItem, storePath);
                result.add(resultItem);
            } else {
                System.out.println("The result item is null");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        CommentScrapping cs = new CommentScrapping("cpu", "sale-desc");
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

    public static String getCookies() {
        return cookies;
    }
}
