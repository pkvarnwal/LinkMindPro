package com.linkmindpro.utils;

public class StringUtils {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValidEmailId(String emailId) {
        return emailId.trim().matches(EMAIL_PATTERN);
    }
}
