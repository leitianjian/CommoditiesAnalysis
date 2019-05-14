package Utils.infoProcess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProcess {
    public static Date getDate (String strDate){
//        System.out.println(strDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            return sdf.parse(strDate);
        } catch (ParseException e){
            System.out.println("Parse failure!");
            return null;
        }
    }
}
