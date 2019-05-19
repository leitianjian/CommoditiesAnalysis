package com.group.commditiesAnalysis.DAO;

import com.group.commditiesAnalysis.model.ItemBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteResult {
    public static void writeResult (ItemBean itemBean, String path){
        String name = "itemID=" + itemBean.getId() + "_" + itemBean.getType();
        File targetFile = new File(path + "/" + name);
        try (PrintWriter out = new PrintWriter(new FileOutputStream(targetFile))){
            out.println(itemBean);
            out.flush();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
