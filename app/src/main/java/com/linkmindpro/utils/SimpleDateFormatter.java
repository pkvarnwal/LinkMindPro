package com.linkmindpro.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleDateFormatter {

    public static String getLastChatTime(String strDate) {
        String outputDate = "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date date;
        try {
            date = inputFormat.parse(strDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentChatTimeStatus(String time) {
        String timeDiff = "";
        try {
            long timeInMillis = Long.parseLong(time); // Convert time into millies
            timeDiff = getDateTimeDiff(timeInMillis);
        } catch (Exception e) {
            timeDiff = getChatTimeStatus(time);
        }
        return timeDiff;
    }

    private static String getDateTimeDiff(long timeInMillis) {
        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        if (timeInMillis < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            timeInMillis *= 1000;
        }

        long now = System.currentTimeMillis();
//		if (timeInMillis > now || timeInMillis <= 0) {
//			return null;
//		}

        // TODO: localize

        final long diff = now - timeInMillis;
        if (diff < MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "A Minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " Minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "An Hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " Hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
//            return diff / DAY_MILLIS + " Days ago";
            return getChatDayAgoFormat(timeInMillis);
        }
    }

    private static String getChatDayAgoFormat(long timeinMillis) {
        String outputDate = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeinMillis);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, hh:mm a");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        outputDate = outputFormat.format(calendar.getTimeInMillis());

        return outputDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getChatTimeStatus(String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date postDate = null;
        try {
            postDate = inputFormat.parse(time);
        } catch (Exception e) {
        }
        long timeInMillis = postDate.getTime(); // Convert time into millies
        return getDateTimeDiff(timeInMillis);

    }
}
