package com.group.commditiesAnalysis.Utils.WebScrapping;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtils {
    public static boolean checkURLValid (String url){
//        System.out.println(url);
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

    public static String getItemId (String url){
        Matcher m = Pattern.compile("[0-9]{7,13}").matcher(url);
        return (m.find()) ? m.group(0) : null;
    }
}
