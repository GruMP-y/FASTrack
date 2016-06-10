package com.weserv.grumpy.assetracker.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountMgmtUtils {
    private static Pattern pattern;
    private static Matcher matcher;
    //Email Pattern
    private static final String EMAIL_PATTERN = "\"[a-zA-Z0-9\\\\+\\\\.\\\\_\\\\%\\\\-\\\\+]{1,256}\" +\n" +
            "          \"\\\\@\" +\n" +
            "          \"[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,64}\" +\n" +
            "          \"(\" +\n" +
            "          \"\\\\.\" +\n" +
            "          \"[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,25}\" +\n" +
            "          \")+\"";


    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static boolean confirmPassword(String newPassword, String confirmPassword) {
        Boolean value = false;
        if (newPassword.equals(confirmPassword)) {
            value = true;
        }

        return value;
    }

    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0 ? true : false;
    }


    public static String generateHash(String input) {
        try {
            if (input != "") {
                MD5HashGenerator generator = new MD5HashGenerator();
                return generator.generateHash(input);
            } else {
                return "";
            }
        } catch (Exception ex) {
            return "";
        }
    }
}
