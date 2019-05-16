package com.group.commditiesAnalysis.DAO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ReadCommentsFromFile {
    private ArrayList<String> comments;
    private String id;
    private static final String HEAD = "itemID=";

    public ReadCommentsFromFile (String id){
        this.id = id;
        this.comments = new ArrayList<>();
        readFile();
    }

    private void readFile (){
        String fileName = HEAD + id;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("utf-8")));
            String temp;
            while ((temp = br.readLine()) != null){
                if (temp.matches(".*rateContent.*")){
                    String rate = temp.split("rateContent")[2];
                    comments.add(rate);
                }
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getComments() {
        return comments;
    }
}
