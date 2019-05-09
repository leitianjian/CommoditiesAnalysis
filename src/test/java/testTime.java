import org.apache.commons.text.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testTime {
    public static void main(String[] args) {
        long timeMills = Long.parseLong("1554524176000");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd: HH mm");
        Date result = new Date(timeMills);
        System.out.println(sdf.format(result));

        try {
            String myString = "https://detail.tmall.com/item.htm?id\\u003d582158874560\\u0026ns\\u003d1\\u0026abbucket\\u003d9\"";
            System.out.println(StringEscapeUtils.unescapeJava(myString));

            byte[] utf8Bytes = myString.getBytes("UTF8");
            String text = new String(utf8Bytes,"UTF8");
//            System.out.println(text);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
