package com.group.commditiesAnalysis.Utils.commentProcess;

import com.group.commditiesAnalysis.DAO.ReadCommentsFromFile;
import com.group.commditiesAnalysis.model.ItemBean;
import com.group.commditiesAnalysis.model.Word;
import com.lingjoin.nlpir.NLPIR;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommentsAnalyzer {
    ArrayList<ItemBean> itemList;
    ArrayList<String> comments;

    // should be deleted in next version
    ArrayList<File> fileList;

    public CommentsAnalyzer (){
        fileList = new ArrayList<>();
        File file = new File(System.getProperty("user.dir"));
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files){
                if (f.getPath().matches(".*itemID.*")){
                    fileList.add(f);
                    System.out.println(f.getPath());
                }
            }
        }
        comments = new ArrayList<>();

        for (File f : fileList){
            ArrayList<String> temp = new ReadCommentsFromFile(fileList.get(1)).getComments();
            if (temp != null) {
                comments.addAll(temp);
            }
        }
//        System.out.println(comments.size());
        HashMap<Word, Integer> result = new HashMap<>();
        NLPIR.init("lib");
//        NLPIR.importUserDict("/home/ltj/Downloads/JAVA2Project/lib/UserDic/professions.txt");
//        NLPIR.importUserDict("/home/ltj/Downloads/JAVA2Project/lib/UserDic/THUOCL_caijing.txt");
        NLPIR.importUserDict("/home/ltj/Downloads/JAVA2Project/lib/UserDic/THUOCL_it.txt");

        Pattern pattern = Pattern.compile(".+?/[a-z0-9]+?/[0-9]+?#");
        StringBuilder sb = new StringBuilder();
        for (String str : comments){
            str = str.replaceAll(" ", "");
            str = str.replaceAll("]", " ");
            str = str.replaceAll(",\\[", "。");
            String st = NLPIR.wordFreqStat(str);
//            System.out.println(st);
            if (st != null) {
                Matcher matcher = pattern.matcher(st);
                while (matcher.find()){
                    String matchResult = matcher.group(0);
//                    System.out.println("Result: " + matchResult);
                    matchResult = matchResult.substring(0, matchResult.length() - 1);
                    String[] t = matchResult.split("/");
                    if (t.length == 3) {
                        Word tempWord = new Word(t[0], t[1]);

                        int count = Integer.parseInt(t[2]);
//                        System.out.println(t[2]);
                        result.putIfAbsent(tempWord, count);

                        if (result.containsKey(tempWord)) {
                            int o = result.get(tempWord) + count;
                            result.put(tempWord, o);
                        }
                    }
                }
            }
//            String[] t = st.split("#");
//            for (String s : t){
//                System.out.println(s);
//                String[] t2 = s.split("/");
//                System.out.println(t2.length);
//                if (t2.length == 1){
//                    System.out.println(s);
//                }
//                Word tempWord = new Word(t2[0], t2[1]);
//                result.putIfAbsent(tempWord, Integer.parseInt(t2[2]));
//                if (result.containsKey(tempWord)){
//                    int o = result.get(tempWord) + Integer.parseInt(t2[2]);
//                    result.put(tempWord, o);
//                }
//            }

//            sb.append(str);
//            sb.append(" ");
//            System.out.println(str);
        }

//        String[] result = NLPIR.wordFreqStat(sb.toString()).split("#");
        try (PrintWriter out = new PrintWriter("temp")){
            result.entrySet()
                    .stream()
//                    .filter(k -> k.getKey().getType().equals("n"))
                                .sorted(Comparator.comparing(Map.Entry::getValue)).forEach(out::println);

//            result.forEach((k, v) -> out.println(k + " Count: " + v));
//            out.println(sb);
//            for (String str : result){
//                out.println(str);
//                out.flush();
//            }
        } catch (IOException e){
            e.printStackTrace();
        }
//        for (String str : result){
//            System.out.println(str);
//        }
    }

    public static void main(String[] args) {
        CommentsAnalyzer c = new CommentsAnalyzer();
    }
}
