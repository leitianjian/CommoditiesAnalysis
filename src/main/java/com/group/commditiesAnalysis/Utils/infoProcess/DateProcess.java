package com.group.commditiesAnalysis.Utils.infoProcess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProcess {
    public static Date getDate (String strDate, String pattern){
//        System.out.println(strDate);"yyyy年MM月dd日 HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e){
            System.out.println("Parse failure!");
            return null;
        }
    }
}
