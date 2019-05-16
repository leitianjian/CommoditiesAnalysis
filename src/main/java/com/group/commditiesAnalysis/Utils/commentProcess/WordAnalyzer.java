package com.group.commditiesAnalysis.Utils.commentProcess;

import com.group.commditiesAnalysis.DAO.ReadCommentsFromFile;

import java.util.ArrayList;

public class WordAnalyzer {
    String itemID;
    ArrayList<String> comments;

    public WordAnalyzer (String itemID){
        this.itemID = itemID;
        this.comments = new ReadCommentsFromFile(itemID).getComments();
    }

//    public


}
