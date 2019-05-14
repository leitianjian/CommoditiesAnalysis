package Utils.WebScrapping;


import com.ltj.commditiesAnalysis.model.ItemBean;

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
            "thw=cn; t=5413b32d2671eb2636342073d8a8bb40; hng=CN%7Czh-CN%7CCNY%7C156; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; cna=9a+LFI0VKW8CATsovZPkpOuK; v=0; cookie2=3a7c3ce9191957500977ba12d1b8042d; _tb_token_=f66f3e3818eb; tracknick=tb865112960; dnk=tb865112960; enc=eyCWMuP7XA7VE0CxA%2FhfPndIi8IXk6TejjUqdbzeNs55vnQuM%2BeHkEi3ZkChazP5zh4ELTqeCGiFzneaSFozrg%3D%3D; x5sec=7b22726174656d616e616765723b32223a223036323039323032653830636666323632363236643335656534633962313737434e717032655946454d6667767447596f392f4653413d3d227d; unb=3339063457; sg=078; _l_g_=Ug%3D%3D; skt=84fb74a7ef0d65cf; cookie1=BYbypJrFBHlGpA%2Fk0Udjr1le3UJ42lgMtNckvDpivyc%3D; csg=320742f8; uc3=vt3=F8dBy3qKUpsVw3mYJ0E%3D&id2=UNN%2Bz7ZSN%2BFrqA%3D%3D&nk2=F5RNY%2B5a7X8boOQ%3D&lg2=UtASsssmOIJ0bQ%3D%3D; existShop=MTU1NzU1MDU4MQ%3D%3D; lgc=tb865112960; _cc_=Vq8l%2BKCLiw%3D%3D; _nk_=tb865112960; cookie17=UNN%2Bz7ZSN%2BFrqA%3D%3D; tg=0; mt=ci=1_1; uc1=cookie16=Vq8l%2BKCLySLZMFWHxqs8fwqnEw%3D%3D&cookie21=UIHiLt3xThH8t7YQoFNq&cookie15=U%2BGCWk%2F75gdr5Q%3D%3D&existShop=false&pas=0&cookie14=UoTZ48D6WjEmfg%3D%3D&tag=8&lng=zh_CN; _m_h5_tk=c9d52c835cfc1cc2153616febc372182_1557558546602; _m_h5_tk_enc=b56752a34c9e39c775d3add17ebc0efb; whl=-1%260%261550899538051%261557550633623; l=bBjM3Y14vxOCCKE3BOfNmuI8Lkbt5IdfhsPzw4OiDICP_P1APSHFWZ9PMCTJC3GVa6fvJ3uzhKN8B8Yinyzyl; isg=BIeH53ezl8jz1BR36DkyYwUvFjKRJFqN_gdh0Vl1RJZEyKGKYF_DvzDOasgzIDPm";

    public CommentScrapping (String searchItem, String sortParam){
        this.searchItem = searchItem;
        this.sortParam = sortParam;
        Search search = new Search(searchItem, sortParam);
        this.TBAddrList = search.getTBCommoditiesList();
        this.TMAddrList = search.getTMCommoditiesList();
        this.result = new ArrayList<>();

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

    public static void main(String[] args) throws IOException {
        CommentScrapping cs = new CommentScrapping("cpu", "sale-desc");
        ArrayList<ItemBean> temp = cs.getResult();
        for (int i = 0; i < temp.size(); ++ i) {
            ItemBean t = temp.get(i);
            String fileName = "itemID=" + t.getId();
            try (PrintWriter out = new PrintWriter(new FileOutputStream(fileName))){
                out.println(t);
                out.flush();
                out.close();
            }
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
