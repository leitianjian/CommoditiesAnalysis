package com.group.commditiesAnalysis.Utils.WebScrapping;

import org.apache.commons.validator.routines.UrlValidator;

public class WebUtils {
    public static boolean checkURLValid (String url){
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }
}
