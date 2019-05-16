package com.group.commditiesAnalysis.DAO;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ReadCommentsFromFile {
    private ArrayList<String> comments;
    private File file;

    public ReadCommentsFromFile (File file){
        this.file = file;
        this.comments = new ArrayList<>();
        readFile();
    }

    private void readFile (){
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("utf-8")));
            String temp;
            while ((temp = br.readLine()) != null){
                if (temp.matches(".*rateContent.*")){
                    String rate = temp.split("rateContent")[1];
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
