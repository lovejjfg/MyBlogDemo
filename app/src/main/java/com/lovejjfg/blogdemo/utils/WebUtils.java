package com.lovejjfg.blogdemo.utils;

import java.util.List;

/**
 * Created by aspsine on 15-3-24.
 */
public class WebUtils {

    private static final String CSS_LINK_PATTERN = " <link href=\"%s\" type=\"text/css\" rel=\"stylesheet\" />";
    private static final String NIGHT_DIV_TAG_START = "<div class=\"night\">";
    private static final String NIGHT_DIV_TAG_END = "</div>";

    private static final String DIV_IMAGE_PLACE_HOLDER = "class=\"img-place-holder\"";
    private static final String DIV_IMAGE_PLACE_HOLDER_IGNORED = "class=\"img-place-holder-ignored\"";

    public static String BuildHtmlWithCss(String html, List<String> cssUrls, boolean isNightMode) {
        StringBuilder result = new StringBuilder();
        result.append("<style type=\"text/css\">\n" +
                "img {\n" +
                "    max-width: 80%;\n" +
                "\tborder: 0;\n" +
                "\tvertical-align: middle;\n" +
                "\tcolor: transparent;\n" +
                "\tfont-size: 0\n" +
                "}\n" +
                "</style>");
//
//        if (isNightMode) {
//            result.append(NIGHT_DIV_TAG_START);
//        }
        result.append(html);
//        if (isNightMode) {
//            result.append(NIGHT_DIV_TAG_END);
//        }

        return result.toString();
    }

}
