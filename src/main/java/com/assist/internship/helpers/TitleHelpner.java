package com.assist.internship.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleHelpner {

    public static final Pattern VALID_TITLE_REGEX =
            Pattern.compile("^[a-z](?:[a-z0-9.-]{0,18}[a-z0-9])?$", Pattern.CASE_INSENSITIVE);

    public static boolean titleIsValid(String title) {
        Matcher matcher = VALID_TITLE_REGEX .matcher(title);
        return matcher.find();
    }
}
